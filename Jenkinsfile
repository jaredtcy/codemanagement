pipeline {
    agent any

    tools {
        // Correctly specifying Maven and JDK versions as per Jenkins tool configuration
        maven 'maven 3.9.9'
        jdk 'Java JDK 17'
    }

    environment {
        PATH = "$PATH:/opt/homebrew/bin" // Ensuring correct shell paths are available
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/jaredtcy/codemanagement.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Code Quality Check (SonarQube)') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
    }

    post {
        success {
            echo 'Build and tests completed successfully!'
        }
        failure {
            echo 'Build or tests failed.'
        }
    }
}
