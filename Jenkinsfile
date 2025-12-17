pipeline {
    agent any

    tools {
        maven 'M3'
    }

    environment {
        PROJECT_KEY = "string-utils-project"
    }

    stages {

        stage('SCM') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Harshitha1518/k8ssonarqube.git'
            }
        }

        stage('Sonar Analysis') {
            steps {
                withSonarQubeEnv('sonar-k8s') {
                    sh """
                        mvn clean verify
