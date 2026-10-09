pipeline {
    agent any

    tools{
        nodejs 'nodejs-20'
    }
    // declaring variable 
    environment{
        TAG="v1.0.${env.BUILD_NUMBER}" // built-in
        IMG_NAME="jenkins-g12-reactjs"
        DH_USER="lyvanna544"

        FULL_IMG="${DH_USER}/${IMG_NAME}:${TAG}"
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
                docker build -t ${FULL_IMG} -f prod.Dockerfile . 
               """
            }
        }

         stage("Push"){
            steps{
                withCredentials([usernamePassword(credentialsId: 'DH_CREDIT', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {

            sh """

           echo "${PASSWORD}" | docker login -u ${USERNAME} --password-stdin

           docker push "${FULL_IMG}"
            """
}
            }
         }
         stage('Deploy') {
            steps {

            script{
                def userInput= input(
                    id: "deployconfig", 
                    message: "choose envrionment for your deployment", 
                    parameters:[
                        choice(
                            name:"ENVIRONMENT", 
                            choices: ["Local","Stagging","Production"], 
                            description: "Environment for your deployment"
                        )
                    ]
    
                )

                if(userInput=="Local"){
                    echo "Deploy on the jenkins server "
                     sh """
                        docker stop reactjs-app || true 
                        docker rm reactjs-app || true 
                        docker run -dp 3000:80 --name reactjs-app ${FULL_IMG} 
                    """
                }else if(userInput=="Stagging"){
                    echo "Deploy on Stag environment "
                    // your code here 

                }else {
                    // production 
                    echo "Deploy on the Production Environment! "
                    // your code here
                }
            }
                
                
              
            }
        }

      
    }
}
