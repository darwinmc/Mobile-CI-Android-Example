pipeline {
  agent {
    node {
      label 'jenkins_slave1'
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
      }
    }
    stage('Lint') {
      steps {
        sh './gradlew clean lintMockDebug'
        archiveArtifacts(fingerprint: true, artifacts: 'app/build/reports/lint-results-mockDebug.html')
      }
    }
    stage('Detekt') {
      steps {
        sh './gradlew detekt'
        archiveArtifacts(fingerprint: true, artifacts: 'build/reports/detekt/detekt.html')
      }
    }
    stage('Mock UI test') {
      steps {
     // Permission to execute
        sh "chmod +x -R ${env.WORKSPACE}"
     // Call SH
        sh "${env.WORKSPACE}/emulator.sh"
        sh './gradlew connectedMockDebugAndroidTest --info'
        archiveArtifacts(artifacts: 'app/build/reports/androidTests/connected/flavors/MOCK/**/*.*', fingerprint: true)
      }
    }
    stage('Live UI test') {
      when { 
          branch "develop/*" 
      }
      steps {
        sh "chmod +x android-wait-for-emulator"
        sh "./android-wait-for-emulator"
        sh "adb shell input keyevent 82"
        // sh "${env.WORKSPACE}/emulator.sh"
        sh './gradlew connectedLiveDebugAndroidTest'
        archiveArtifacts(artifacts: 'app/build/reports/androidTests/connected/flavors/LIVE/**/*.*', fingerprint: true)
      }
    }
    stage('Coverage') {
      steps {
        // sh "${env.WORKSPACE}/emulator.sh"
        sh './gradlew jacocoTestReport'
        archiveArtifacts(artifacts: 'app/build/reports/coverage/mock/debug/**/*.*', fingerprint: true)
      }
    }
    stage('Sonar analysis') {
      steps {
        withSonarQubeEnv('Sonar') { 
          sh "sonar-scanner -Dsonar.branch.name=${branch}"
        }
      }
    }
    stage('Build') {
      steps {
        sh './gradlew clean'
        sh "mkdir keys"
        sh "cp -r /var/jenkins_home/upload-keystore.jks keys/keystore"
        sh './gradlew assembleRelease'
      }
    }
   /* stage('Deploy to Artifactory') {
      when { 
        branch "develop" 
      }
      steps {
        sh "cp ${mockApp}.apk ${mockAppRenamed}.apk"
        sh "cp ${liveApp}.apk ${liveAppRenamed}.apk"
        sh "jfrog rt u ${mockAppRenamed}.apk mobile-ci-android/hu/dpal/mobileci/${version_number}/${build_number}/mock/${mockAppRenamed}.apk --build-name=MobileCIAndroidMock --build-number=${build_number} --props=\"git=${git_hash}\""
        sh "jfrog rt u ${liveAppRenamed}.apk mobile-ci-android/hu/dpal/mobileci/${version_number}/${build_number}/live/${liveAppRenamed}.apk --build-name=MobileCIAndroidLive --build-number=${build_number} --props=\"git=${git_hash}\""
      }
    }*/
    stage('Deploy to Firebase App Distribution') {
      when { 
        branch "develop" 
      }
      steps {
        sh "firebase appdistribution:distribute ${mockAppRenamed}.apk --app 1:933578923930:android:cc7e2594e7c646fca16423"
        sh "firebase appdistribution:distribute ${liveAppRenamed}.apk --app 1:933578923930:android:f2d2bb63760022daa16423"
      }
    }
    stage('Deploy to Play Beta') {
      when { 
        branch "release/*" 
      }
      steps {
        sh 'echo "TODO: BETA DEPLOYMENT"'
      }
    }
  }
   post {
    always {
      sh 'echo "Destroyed"'
    }
  }
 /* post { 
        always {
             script {
                if (getContext(hudson.FilePath)) {
                    deleteDir()
                }
            } 
        }
    }*/
}
