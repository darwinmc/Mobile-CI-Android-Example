pipeline {
  agent {
    node {
      label 'mac'
    }
  }
  stages {
    stage('Checkout Submodules') {
      steps {
        sh 'git submodule update --init --recursive'
      }
    }
    stage('Sonar test') {
      steps {
        sh 'sonar-scanner'
      }
    }
    stage('Build') {
      steps {
        sh './gradlew clean assembleDebug'
      }
    }
    stage('Lint') {
      steps {
        sh './gradlew lint'
        archiveArtifacts(fingerprint: true, artifacts: 'app/build/reports/lint-results.html')
      }
    }
    stage('Unit test') {
      steps {
        sh './gradlew testMockDebugUnitTest'
        archiveArtifacts(artifacts: 'app/build/reports/tests/testMockDebugUnitTest/**/*.*', fingerprint: true)
      }
    }
    stage('Mock UI test') {
      steps {
        sh './gradlew connectedMockDebugAndroidTest'
        archiveArtifacts(artifacts: 'app/build/reports/androidTests/connected/flavors/MOCK/**/*.*', fingerprint: true)
      }
    }
    stage('Live UI test') {
      when { 
          branch "develop/*" 
      }
      steps {
        sh './gradlew connectedLiveDebugAndroidTest'
        archiveArtifacts(artifacts: 'app/build/reports/androidTests/connected/flavors/LIVE/**/*.*', fingerprint: true)
      }
    }
    stage('Sonar analysis') {
      steps {
        withSonarQubeEnv('Sonar') { 
          sh 'sonar-scanner'
        }
      }
    }
    stage('Deploy fabric') {
      when { 
        branch "develop/*" 
      }
      steps {
        sh 'echo "TODO: FABRIC"'
      }
    }
    stage('Deploy beta') {
      when { 
        branch "release/*" 
      }
      steps {
        sh 'echo "TODO: BETA DEPLOY"'
      }
    }
  }
  post {
    always {
      sh 'echo "TODO: DESTROY"'
    }
  }
}