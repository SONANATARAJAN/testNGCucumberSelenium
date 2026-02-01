pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Create Docker Network') {
            steps {
                sh '''
                docker network inspect selenium-net >/dev/null 2>&1 || \
                docker network create selenium-net
                '''
            }
        }

      stage('Start Selenium Container') {
    steps {
        sh '''
        docker rm -f selenium || true

        docker run -d \
          --name selenium \
          --network selenium-net \
          --shm-size="2g" \
          selenium/standalone-chrome:4.17.0

        echo "⏳ Waiting for Selenium Grid to be READY..."

        until curl -s http://selenium:4444/status | grep -q '"ready":true'; do
          sleep 2
        done

        echo "✅ Selenium Grid is READY"
        '''
    }
}



        stage('Build Test Image') {
            steps {
                sh '''
                docker build -t cucumber-tests .
                '''
            }
        }

       stage('Run Tests') {
    steps {
        sh '''
        docker run --rm \
          --network selenium-net \
          cucumber-tests \
          ./gradlew test -Dremote=true
        '''
    }
}

    }

    post {
        always {
            sh '''
            docker rm -f selenium || true
            '''
        }
        success {
            echo '✅ Gradle Tests passed successfully'
        }
        failure {
            echo '❌ Gradle Tests failed'
        }
    }
}
