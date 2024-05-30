
pipeline {

    agent any
    options {
        durabilityHint 'MAX_SURVIVABILITY'
    }
    stages {
        stage('Checkout') {
            steps {
                sh 'rm -rf vacancy_backend; git clone https://github.com/cherepakhin/vacancy_backend'
            }
        }

        stage('Unit tests') {
            steps {
                sh 'pwd;cd vacancy_backend;./gradlew clean test --tests *Test'
            }
        }

        stage('Integration tests') {
            steps {
                sh 'pwd;cd vacancy_backend;./gradlew clean test --tests *TestIntegration'
            }
        }

        stage('Build bootJar') {
            steps {
                sh 'pwd;cd vacancy_backend;./gradlew bootJar'
            }
        }

        stage('Publish to Nexus') {
            environment {
                NEXUS_CRED = credentials('nexus_admin')
            }
            steps {
                sh 'cd vacancy_backend;./gradlew publish'
            }
        }
    }
}