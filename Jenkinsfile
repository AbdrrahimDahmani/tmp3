pipeline {
    agent any
    
    tools {
        maven 'Maven 3.8.6'
        jdk 'JDK 17'
    }
    
    environment {
        DOCKER_REGISTRY = 'your-docker-registry'
        PROJECT_NAME = 'banking-microservices'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building Maven project...'
                sh 'mvn clean install -DskipTests'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }
        
        stage('Code Quality Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                // Uncomment when SonarQube is configured
                // sh 'mvn sonar:sonar'
            }
        }
        
        stage('Build Docker Images') {
            steps {
                echo 'Building Docker images...'
                sh 'docker-compose build'
            }
        }
        
        stage('Push Docker Images') {
            steps {
                echo 'Pushing Docker images to registry...'
                script {
                    // Uncomment and configure when Docker registry is available
                    // docker.withRegistry("https://${DOCKER_REGISTRY}", 'docker-credentials') {
                    //     sh 'docker-compose push'
                    // }
                }
            }
        }
        
        stage('Deploy to Dev') {
            steps {
                echo 'Deploying to Development environment...'
                sh 'docker-compose down || true'
                sh 'docker-compose up -d'
            }
        }
        
        stage('Integration Tests') {
            steps {
                echo 'Running integration tests...'
                // Wait for services to be healthy
                sh 'sleep 60'
                // Add integration tests here
            }
        }
    }
    
    post {
        always {
            echo 'Cleaning up...'
            // Clean up workspace
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
