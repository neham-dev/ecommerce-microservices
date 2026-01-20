pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'neha2917'
        DOCKERHUB_CREDENTIALS = 'dockerhub-creds'
        TAG = "${BUILD_NUMBER}"
    }

    options {
        timestamps()
    }

    stages {

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/neham-dev/ecommerce-microservices.git'
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: DOCKERHUB_CREDENTIALS,
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                    set -e
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                    '''
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                sh '''
                set -e
                docker build -t $DOCKERHUB_USER/api-gateway:$TAG ./api-gateway
                docker build -t $DOCKERHUB_USER/service-registry:$TAG ./service-registry
                docker build -t $DOCKERHUB_USER/user-service:$TAG ./user-service
                docker build -t $DOCKERHUB_USER/order-service:$TAG ./order-service
                docker build -t $DOCKERHUB_USER/inventory-service:$TAG ./inventory-service

                docker tag $DOCKERHUB_USER/api-gateway:$TAG $DOCKERHUB_USER/api-gateway:latest
                docker tag $DOCKERHUB_USER/service-registry:$TAG $DOCKERHUB_USER/service-registry:latest
                docker tag $DOCKERHUB_USER/user-service:$TAG $DOCKERHUB_USER/user-service:latest
                docker tag $DOCKERHUB_USER/order-service:$TAG $DOCKERHUB_USER/order-service:latest
                docker tag $DOCKERHUB_USER/inventory-service:$TAG $DOCKERHUB_USER/inventory-service:latest
                '''
            }
        }

        stage('Push Images to DockerHub') {
            steps {
                sh '''
                set -e
                docker push $DOCKERHUB_USER/api-gateway:$TAG
                docker push $DOCKERHUB_USER/api-gateway:latest

                docker push $DOCKERHUB_USER/service-registry:$TAG
                docker push $DOCKERHUB_USER/service-registry:latest

                docker push $DOCKERHUB_USER/user-service:$TAG
                docker push $DOCKERHUB_USER/user-service:latest

                docker push $DOCKERHUB_USER/order-service:$TAG
                docker push $DOCKERHUB_USER/order-service:latest

                docker push $DOCKERHUB_USER/inventory-service:$TAG
                docker push $DOCKERHUB_USER/inventory-service:latest
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Docker images built and pushed successfully!"
        }
        failure {
            echo "❌ Pipeline failed. Check logs."
        }
    }
}
