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
                        mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=${PROJECT_KEY} \
                        -Dsonar.projectName=${PROJECT_KEY}
                    """
                }
            }
        }

        stage('Quality Gate Validate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Publish to Nexus') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'nexus-creds',
                    usernameVariable: 'NEXUS_USER',
                    passwordVariable: 'NEXUS_PASS'
                )]) {
                    sh '''
                        mvn deploy -DskipTests
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "Build successful and artifact pushed to Nexus"
        }
        failure {
            echo "Pipeline failed"
        }
    }
}

  
