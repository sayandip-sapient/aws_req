import jenkins.branch.BranchSource
import jenkins.model.*
import jenkins.plugins.git.GitSCMSource
import jenkins.scm.api.SCMSource
import jenkins.scm.api.SCMSourceCriteria
import hudson.model.TaskListener
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition

String multibranchPipelineJobName = 'hirebuddy-multibranch-pipeline'
String gitRepoUrl = 'https://github.com/sayandip-sapient/jenkins_proj.git'
String branchName = 'main'

// Create the Multibranch Pipeline job only if it doesn't exist
if (Jenkins.instance.getItem(multibranchPipelineJobName) == null) {
    // Create a new Multibranch Pipeline job
    def multibranchJob = Jenkins.instance.createProject(WorkflowMultiBranchProject.class, multibranchPipelineJobName)

    // Configure the Git repository
    SCMSourceCriteria criteria = new SCMSourceCriteria() {
        @Override
        boolean isHead(SCMSourceCriteria.Probe probe, TaskListener listener) {
            return probe.fileExists('Jenkinsfile') || probe.fileExists('jenkinsfile')
        }
    }
    
    SCMSource source = new GitSCMSource(null, gitRepoUrl, '', '*', '', false)
    source.setCriteria(criteria)

    // Create a branch source and add it to the Multibranch Pipeline job
    def branchSource = new BranchSource(source)
    multibranchJob.getSourcesList().add(branchSource)

    // Set the default Jenkinsfile Flow Definition
    multibranchJob.setDefinition(new CpsScmFlowDefinition("Jenkinsfile", true))

    multibranchJob.save()
}