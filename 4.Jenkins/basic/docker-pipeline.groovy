pipeline {
    agent{
        docker{ 
            image "node:22.23.2"
            args "-u root"
        }
    }

    stages {
        stage("Checkout"){
            steps{
                git 'https://github.com/keoKAY/reactjs-devop8-template'
          
            }
        }
        stage("Running NPM"){
            steps{
                sh """
                node -v 
                npm -v 
                ls -lrt 
                npm install 
                npm run build 
                npm run test 
                """
            }
        }
    }
}