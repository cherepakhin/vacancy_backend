
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

        stage('Build bootJar') {
            steps {
                sh 'pwd;cd vacancy_backend;./gradlew bootJar'
            }
        }

        stage('Publish to Nexus') {
            environment {
                NEXUS_CRED = credentials('vasi')
            }
            steps {
                sh 'export NEXUS_CI_USER=admin; export NEXUS_CI_PASS=pass;echo $NEXUS_CI_USER;cd vacancy_backend;ls;./gradlew publish'
            }
        }
    }
}