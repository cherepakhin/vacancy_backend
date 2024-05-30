
pipeline {

    agent any
    options {
        durabilityHint 'MAX_SURVIVABILITY'
    }
    stages {
        stage('Checkout') {
            steps {
                sh 'rm -rf vacancy_kotlin; git clone https://github.com/cherepakhin/vacancy_kotlin'
            }
        }

        stage('Unit tests') {
            steps {
                sh 'pwd;cd vacancy_kotlin;./gradlew clean test --tests *Test'
            }
        }

        stage('Build bootJar') {
            steps {
                sh 'pwd;cd vacancy_kotlin;./gradlew bootJar'
            }
        }

        stage('Publish to Nexus') {
            environment {
                NEXUS_CRED = credentials('nexus_admin')
            }
            steps {
                sh 'cd vacancy_kotlin;./gradlew publish'
            }
        }
    }
}