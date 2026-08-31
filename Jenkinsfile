pipeline {
    agent any

    tools {
        jdk 'JDK19'
        maven 'Maven3.9'
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Compile and Test') {
            steps { bat 'mvn -B clean test' }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/cucumber-reports/**', allowEmptyArchive: true
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
    }
}
