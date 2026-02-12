pipeline {
  agent any
  options {
    timestamps()
    buildDiscarder(logRotator(numToKeepStr: '15'))
  }
  environment {
    MVN_ARGS = '-B -Djansi=false -Djdk.attach.allowAttachSelf=true'
  }
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }
    stage('Build & Test') {
      steps {
        sh "mvn ${MVN_ARGS} clean verify"
      }
    }
  }
  post {
    always {
      junit allowEmptyResults: true, testResults: 'target/failsafe-reports/*.xml'
      archiveArtifacts artifacts: 'target/site/serenity/**/*', allowEmptyArchive: true
    }
    success {
      archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: true
    }
  }
}
