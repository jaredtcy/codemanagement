pipeline {
    agent any

    environment {
        // Defining environment variables for Maven and JDK tools
        MAVEN_HOME = tool name: 'maven 3.9.9', type: 'Maven'
        JDK_HOME = tool name: 'JDK 17', type: 'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                // Cloning the repository from GitHub
                git branch: 'main', url: 'https://github.com/jaredtcy/codemanagement.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Running Maven build command to compile the code
                    sh "'${MAVEN_HOME}/bin/mvn' clean install -DskipTests"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Running Maven test command to execute unit tests
                    sh "'${MAVEN_HOME}/bin/mvn' test"
                }
            }
        }

        stage('Code Quality Check (SonarQube)') {
            steps {
                script {
                    // Running SonarQube analysis to check code quality
                    sh "'${MAVEN_HOME}/bin/mvn' sonar:sonar"
                }
            }
        }
    }

    post {
        success {
            // Notify on successful build and test completion
            echo 'Build and tests completed successfully!'
        }
        failure {
            // Notify on failure
            echo 'Build or tests failed.'
        }
    }
}
