pipeline {
    agent any

    tools {
        maven 'maven 3.9.9'
        jdk 'Java JDK 17'
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
                // Running Maven build command to compile the code
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Running Maven test command to execute unit tests
                sh 'mvn test'
            }
        }

        stage('Code Quality Check (SonarQube)') {
            steps {
                // Running SonarQube analysis to check code quality
                sh 'mvn sonar:sonar'
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
