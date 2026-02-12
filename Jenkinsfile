pipeline {
  agent {
        docker {
            image 'markhobson/maven-chrome:jdk-11'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
  options {
    timestamps()
    buildDiscarder(logRotator(numToKeepStr: '15'))
  }
  environment {
    MVN_ARGS = '-B -Djansi=false -Djdk.attach.allowAttachSelf=true -Dwebdriver.autodownload=true -Dheadless.mode=true'
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
      publishSerenity()
    }
    success {
      archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: true
    }
  }
}
