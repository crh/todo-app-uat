pipeline {
    agent any
    tools {
        maven 'M3' // Must match the name in Global Tool Configuration
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/crh/todo-app-uat.git'
            }
        }
        stage('Run Serenity Tests') {
            steps {
                // 'verify' is used because it triggers the 'aggregate' goal of Serenity
                sh 'mvn clean verify -B -Djansi=false -Djdk.attach.allowAttachSelf=true -Dwebdriver.autodownload=true -Dwebdriver.driver=chrome -Dheadless.mode=true'
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/failsafe-reports/*.xml'
            archiveArtifacts artifacts: 'target/site/serenity/**/*', allowEmptyArchive: true
            // Using the Serenity BDD Plugin step
            publishSerenity()

            // Optional: Backup using HTML Publisher to ensure screenshots are visible
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/site/serenity',
                reportFiles: 'index.html',
                reportName: 'Serenity HTML Report'
            ])
        }
    }
}