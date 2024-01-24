import jenkins.model.*
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
String seedJobName = 'spring-boot-seed-job'
String gitRepoUrl = 'https://github.com/sayandip-sapient/jenkins_proj.git'
String jobDslScriptPath = 'jobdsl.groovy'
String branchName = 'main'

// Create the seed job only if it doesn't exist
if (Jenkins.instance.getItem(seedJobName) == null) {
    // Define the DSL job script
    def dslScript = """
        pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Clone the Git repository
                git branch: 'main', url: 'https://github.com/sayandip-sapient/jenkis_proj.git'
            }
        }

        stage('Build') {
            steps {
                // Use Maven to build the Spring Boot application
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                // Run tests if applicable
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                // Deploy the Spring Boot application (e.g., to a server)
                echo 'Deployment steps go here...'
            }
        }
    }

    post {
        success {
            echo 'Build successful! Sending notifications...'
            // Add your success notification steps here
        }

        failure {
            echo 'Build failed! Sending notifications...'
            // Add your failure notification steps here
        }
    }
}


    """

    // Create seed job
    def seedJob = Jenkins.instance.createProject(WorkflowJob.class, seedJobName)
    seedJob.definition = new org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition(dslScript, true)
    seedJob.save()

    // Trigger the seed job manually (optional)
    seedJob.scheduleBuild2(0)
}
     