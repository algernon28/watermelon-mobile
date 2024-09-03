pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh 'appium'
        sh 'adb start-server'
        sleep 2
        sh '/development/Android/Android_SDK/emulator/emulator.exe -avd Pixel_4_XL_API_30'
      }
    }

    stage('Maven') {
      agent any
      steps {
        withMaven(mavenOpts: 'cluecumber-report:reporting -Dstage=config-dev.yaml -DtestSuite=test-suites/Test-Android.yaml -Dcucumber.filter.tags="@login" -Dlog.dir=/temp -DappFile=apps/Android-MyDemoAppRN.1.3.0.build-244.apk -DappID=com.saucelabs.mydemoapp.rn --add-opens java.base/java.lang=ALL-UNNAMED', jdk: 'JDK 17', maven: 'Maven 3.8.6', traceability: true) {
          bat(script: 'mvn clean install', returnStatus: true, returnStdout: true, label: 'Maven')
        }

        sh 'mvn clean install'
      }
    }

  }
}