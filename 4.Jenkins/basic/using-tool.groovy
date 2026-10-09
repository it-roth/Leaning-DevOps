pipeline {
    agent any

    tools{
        nodejs 'nodejs-20'
    }
    stages {
        stage("Checkout"){
            steps{
                git 'https://github.com/keoKAY/reactjs-devop8-template'
          
            }
        }
        stage('Run Test Tool') {
        when{
            expression {
                params.RUN_TEST == true 
            }

        }
            steps {
                sh """
                node -v
                npm -v 
                echo "RUN_TEST IS: ${params.RUN_TEST}"
                npm i # install dependencies 
                # run test 
                npm run test 

                """
                }
        }
         stage('Build') {
            steps {
               sh "ls -lrt "
               sh """
                docker build -t jenkins-react:latest -f prod.Dockerfile . 
               """
            }
        }

         stage('Deploy') {
            steps {
                
               sh """
                docker stop reactjs-app || true 
                docker rm reactjs-app || true 
                docker run -dp 3000:80 --name reactjs-app jenkins-react:latest 
               """
            }
        }

      
    }
}
