pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                // Add build steps here (e.g., compilation, packaging)
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                // Add test steps here (e.g., unit tests, integration tests)
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying...'
                // Add deployment steps here (e.g., deploying to a server or a cloud platform)
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
            // Add post-build steps for success
        }
        failure {
            echo 'Pipeline failed. Performing cleanup steps...'
            // Add post-build steps for failure or cleanup
        }
    }
}
