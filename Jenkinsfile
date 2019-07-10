pipeline {
  agent {
    node {
      label 'mac'
    }
  }
  environment {
    build_number = sh(returnStdout: true, script: './gradlew -q printVersionCode').trim()
    version_number = sh(returnStdout: true, script: './gradlew -q printVersionName').trim()
    version_number_filename = version_number.replaceAll("\\.", "_")
    git_hash = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
    branch = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()

    mockApp = "app/build/outputs/apk/mock/release/app-mock-release"
    liveApp = "app/build/outputs/apk/live/release/app-live-release"
    fileNameExt = "${version_number_filename}-build-${build_number}-git-${git_hash}"
    mockAppRenamed =  "${mockApp}_${fileNameExt}"
    liveAppRenamed =  "${liveApp}_${fileNameExt}"
  }
  stages {
    stage('Checkout Submodules') {
      steps {
        sh 'git submodule update --init --recursive'
        sh 'cp keys/fabric.properties app/fabric.properties'
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
        sh './gradlew clean testMockDebugUnitTest'
        archiveArtifacts(artifacts: 'app/build/reports/tests/testMockDebugUnitTest/**/*.*', fingerprint: true)
      }
    }
    stage('Mock UI test') {
      steps {
        sh './gradlew clean connectedMockDebugAndroidTest'
        archiveArtifacts(artifacts: 'app/build/reports/androidTests/connected/flavors/MOCK/**/*.*', fingerprint: true)
      }
    }
    stage('Live UI test') {
      when { 
          branch "develop/*" 
      }
      steps {
        sh './gradlew clean connectedLiveDebugAndroidTest'
        archiveArtifacts(artifacts: 'app/build/reports/androidTests/connected/flavors/LIVE/**/*.*', fingerprint: true)
      }
    }
    stage('Coverage') {
      steps {
        sh './gradlew clean jacocoTestReport'
        archiveArtifacts(artifacts: 'app/build/reports/coverage/mock/debug/**/*.*', fingerprint: true)
      }
    }
    stage('Sonar analysis') {
      steps {
        withSonarQubeEnv('Sonar') { 
          sh "sonar-scanner -Dsonar.branch=${branch}"
        }
      }
    }
    stage('Build') {
      steps {
        sh './gradlew clean assembleRelease'
      }
    }
    stage('Deploy artifactory') {
      when { 
        branch "develop" 
      }
      steps {
        sh "cp ${mockApp}.apk ${mockAppRenamed}.apk"
        sh "cp ${liveApp}.apk ${liveAppRenamed}.apk"
        sh "jfrog rt u ${mockAppRenamed}.apk mobile-ci-android/hu/dpal/mobileci/${version_number}/${build_number}/mock/${mockAppRenamed}.apk --build-name=MobileCIAndroidMock --build-number=${build_number} --props=\"git=${git_hash}\""
        sh "jfrog rt u ${liveAppRenamed}.apk mobile-ci-android/hu/dpal/mobileci/${version_number}/${build_number}/live/${liveAppRenamed}.apk --build-name=MobileCIAndroidLive --build-number=${build_number} --props=\"git=${git_hash}\""
      }
    }
    stage('Deploy fabric') {
      when { 
        branch "develop" 
      }
      steps {
        sh './gradlew crashlyticsUploadDistributionLiveRelease'
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