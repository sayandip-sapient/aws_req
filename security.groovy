import jenkins.model.*
import hudson.security.*

def env = System.getenv()

if (env.JENKINS_ADMIN_ID && env.JENKINS_ADMIN_PASSWORD) {
    def jenkins = Jenkins.getInstance()
    jenkins.setSecurityRealm(new HudsonPrivateSecurityRealm(false))
    jenkins.setAuthorizationStrategy(new GlobalMatrixAuthorizationStrategy())

    def adminUser = jenkins.getSecurityRealm().createAccount(env.JENKINS_ADMIN_ID, env.JENKINS_ADMIN_PASSWORD)
    adminUser.save()

    jenkins.getAuthorizationStrategy().add(Jenkins.ADMINISTER, env.JENKINS_ADMIN_ID)
    jenkins.save()
}