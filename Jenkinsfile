pipeline {
    agent any

    tools {
        maven 'M3'
    }

    environment {
        PROJECT_KEY = "string-utils-project"
        IMAGE_NAME  = "string-utils"
        VERSION     = "1.0.${BUILD_NUMBER}"
        GROUP_ID    = "com.example"
        ARTIFACT_ID= "string-utils"
        NEXUS_URL  = "http://13.200.17.237:30002"
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
                sh 'mvn clean package -Drevision=${VERSION}'
            }
        }

        stage('Nexus-artifactory') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: '13.200.17.237:30002',
                    groupId: "${GROUP_ID}",
                    version: "${VERSION}",
                    repository: 'maven-releases',
                    credentialsId: 'harshi',
                    artifacts: [
                        [
                            artifactId: "${ARTIFACT_ID}",
                            classifier: '',
                            file: "target/${ARTIFACT_ID}-${VERSION}.jar",
                            type: 'jar'
                        ]
                    ]
                )
            }
        }

        stage('Build Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'harshi',
                    usernameVariable: 'NEXUS_USER',
                    passwordVariable: 'NEXUS_PASS'
                )]) {
                    
                    sh """
                    docker build \
                    --build-arg NEXUS_USER=${NEXUS_USER} \
                    --build-arg NEXUS_PASS=${NEXUS_PASS} \
                    --build-arg NEXUS_URL=${NEXUS_URL} \
                    --build-arg GROUP_ID=${GROUP_ID} \
                    --build-arg ARTIFACT_ID=${ARTIFACT_ID} \
                    --build-arg VERSION=${VERSION} \
                    -t ${IMAGE_NAME}:${VERSION} .
                    """
                }
            }
        }

        stage('Push Image to ECR') {
            steps {
                withAWS(credentials: 'ECR-user', region: 'ap-south-1') {

                    sh """
                    aws ecr get-login-password --region ap-south-1 | \
                    docker login --username AWS --password-stdin \
                    426728254540.dkr.ecr.ap-south-1.amazonaws.com

                    docker tag ${IMAGE_NAME}:${VERSION} \
                    426728254540.dkr.ecr.ap-south-1.amazonaws.com/string-utils:${VERSION}

                    docker push \
                    426728254540.dkr.ecr.ap-south-1.amazonaws.com/string-utils:${VERSION}
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Docker image successfully pushed to Amazon ECR"
        }
        failure {
            echo "Pipeline failed during Docker/ECR stage"
        }
    }
}
