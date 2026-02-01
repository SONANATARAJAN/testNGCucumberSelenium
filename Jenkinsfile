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

                echo "⏳ Waiting for Selenium to be READY..."

                for i in {1..30}; do
                  if curl -s http://selenium:4444/status | grep -q '"ready": true'; then
                    echo "✅ Selenium is READY"
                    break
                  fi
                  sleep 2
                done
                '''
            }
        }

        stage('Build Gradle Test Image') {
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
              gradle test -Dremote=true --no-daemon
        '''
    }
}

    }

    post {
        always {
            sh 'docker rm -f selenium || true'
        }
        success {
            echo '✅ Gradle + Selenium tests passed'
        }
        failure {
            echo '❌ Tests failed'
        }
    }
}
