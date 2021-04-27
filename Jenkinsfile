#!/usr/bin/env groovy

node('iasset-jenkins-slave') {

    // -----------------------------------------------
    // --------------- Staging Branch ----------------
    // -----------------------------------------------
    if (env.BRANCH_NAME == 'staging') {

        stage('Clone and Update') {
            git(url: 'https://github.com/i-Asset/asset-registry.git', branch: env.BRANCH_NAME)
        }

        stage('Build Dependencies') {
            sh 'rm -rf common'
            sh 'git clone https://github.com/i-Asset/common.git'
            dir('common') {
                sh 'git checkout ' + env.BRANCH_NAME
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Build Java') {
            sh 'mvn clean install -DskipTests'
        }

        stage('Build Docker') {
            sh 'mvn docker:build -Ddocker.image.tag=staging'
        }

        stage('Push Docker') {
            sh 'docker push iassetplatform/asset-registry:staging'
        }

        stage('Deploy') {
            sh 'ssh staging "cd /srv/docker-setup/staging/ && ./run-staging.sh restart-single registry-service"'
        }
    }

    // -----------------------------------------------
    // ---------------- Master Branch ----------------
    // -----------------------------------------------
    if (env.BRANCH_NAME == 'master') {

        stage('Clone and Update') {
            git(url: 'https://github.com/i-Asset/asset-registry.git', branch: env.BRANCH_NAME)
        }

        stage('Build Dependencies') {
            sh 'rm -rf common'
            sh 'git clone https://github.com/i-Asset/common.git'
            dir('common') {
                sh 'git checkout ' + env.BRANCH_NAME
                sh 'mvn clean install'
            }

            sh 'rm -rf basyx'
            sh 'git clone https://github.com/i-Asset/basyx.git'
            dir('basyx/sdks/java/basys.sdk') {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Build Java') {
            sh 'mvn clean install -DskipTests'
        }

        stage('Build Docker') {
            sh 'mvn docker:build -Ddocker.image.tag=latest'
        }
    }

    // -----------------------------------------------
    // ---------------- Release Tags -----------------
    // -----------------------------------------------
    if( env.TAG_NAME ==~ /^\d+.\d+.\d+$/) {

        stage('Clone and Update') {
            git(url: 'https://github.com/i-Asset/asset-registry.git', branch: env.BRANCH_NAME)
        }

        stage('Set version') {
            sh 'mvn org.codehaus.mojo:versions-maven-plugin:2.1:set -DnewVersion=' + env.TAG_NAME
        }

//        stage('Run Tests') {
//            sh 'mvn clean test'
//        }

        stage('Build Java') {
            sh 'mvn clean install -DskipTests'
        }

        stage('Build Docker') {
            sh 'mvn docker:build'
        }

        stage('Push Docker') {
            sh 'docker push iassetplatform/asset-registry:' + env.TAG_NAME
            sh 'docker push iassetplatform/asset-registry:latest'
        }

        stage('Deploy PROD') {
            sh 'ssh prod "cd /data/deployment_setup/prod/ && sudo ./run-prod.sh restart-single asset-registry"'
        }

    }
}
