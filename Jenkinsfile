pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'neha2917'
        DOCKERHUB_CREDENTIALS = 'dockerhub-creds'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/neham-dev/ecommerce-microservices.git'
            }
        }

        stage('Build Docker Images') {
            steps {
                sh '''
                docker build -t $DOCKERHUB_USER/api-gateway ./api-gateway
                docker build -t $DOCKERHUB_USER/service-registry ./service-registry
                docker build -t $DOCKERHUB_USER/user-service ./user-service
                docker build -t $DOCKERHUB_USER/order-service ./order-service
                docker build -t $DOCKERHUB_USER/inventory-service ./inventory-service
                '''
            }
        }

        stage('Login to DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: DOCKERHUB_CREDENTIALS,
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                    '''
                }
            }
        }

        stage('Push Images to DockerHub') {
            steps {
                sh '''
                docker push $DOCKERHUB_USER/api-gateway
                docker push $DOCKERHUB_USER/service-registry
                docker push $DOCKERHUB_USER/user-service
                docker push $DOCKERHUB_USER/order-service
                docker push $DOCKERHUB_USER/inventory-service
                '''
            }
        }
    }
}
