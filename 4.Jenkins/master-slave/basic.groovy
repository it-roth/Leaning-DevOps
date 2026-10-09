pipeline{
    agent any 
    environment{
        TAG="v1.0.${env.BUILD_NUMBER}" // built-in
        IMG_NAME="jenkins-g12-reactjs"
        DH_USER="lyvanna544"
        FULL_IMG="${DH_USER}/${IMG_NAME}:${TAG}"
        // for telegram message
         CHAT_ID="your-telegram-chat-id"
        TOKEN="your-bot-token"
    }
    stages{
        stage("Checkout"){
            steps{
                git 'https://github.com/keoKAY/reactjs-devop8-template'         
            }
        }
        stage("Build"){
            steps{
                sh """
                docker build -t ${FULL_IMG} -f prod.Dockerfile .
                """
            }
        }

       stage("Push to Dockerhub"){
            steps{
                withCredentials([usernamePassword(credentialsId: 'DH_CREDIT', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {

            sh """

           echo "${PASSWORD}" | docker login -u ${USERNAME} --password-stdin

           docker push "${FULL_IMG}"
            """
}
            }
         }

        stage("Deploy Service"){
            agent{
                label 'slave1'
            }
            steps{
                sh """ 

                docker stop my-app || true 
                docker rm my-app || true                
                docker run -dp 3000:80 --name my-app ${FULL_IMG}
                """
            }
        }
    }

 post{
    always{
        echo "This always run regardless of the pipeline result"
    }
       failure{
        echo "Pipeline is failure! "
        script{
        def message = """
Jenkins pipeline result in failure !! 
Your pipeline is failed!

        """
        sendTelegramMessage("${message}", "${TOKEN}","${CHAT_ID}")

        }
    }
    success{
        echo "Pipeline is success! "
        script{
        def message = """
Congratulation !! 
Your pipeline is success!

        """
        sendTelegramMessage("${message}", "${TOKEN}","${CHAT_ID}")

        }
    }
 }
}

def sendTelegramMessage(String message, String token, String chatId){
    sh """
     curl -s -X POST "https://api.telegram.org/bot${token}/sendMessage" -d chat_id="${chatId}" -d parse_mode="Markdown"  -d text="${message}"
    """

}