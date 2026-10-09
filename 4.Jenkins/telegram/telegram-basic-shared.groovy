pipeline{
    agent any 
    environment{
        CHAT_ID="your-chat-id"
        TOKEN="your-token"
    }
stages{
        stage("Send Telegram Message"){
            steps{
                script{
                    def message="""
                    **Hello from jenkins** 
                    *THis is th message from your pipeline*
                    - First 
                    - Second 
                    > Testing 
                    """ 
                    sendTelegramMessage("${message}", "${TOKEN}","${CHAT_ID}")
                }
               
            }
        }
    }
}


def sendTelegramMessage(String message, String token, String chatId){
    sh """
     curl -s -X POST "https://api.telegram.org/bot${token}/sendMessage" -d chat_id="${chatId}" -d parse_mode="Markdown"  -d text="${message}"
    """

}

def sendTelegramMessageV2(String message, String token , String chatId) {
  
    def encodedMessage = URLEncoder.encode(message, "UTF-8")
    sh """
        curl -s -X POST https://api.telegram.org/bot${token}/sendMessage \\
        -d chat_id=${chatId} \\
        -d text="${encodedMessage}" > /dev/null
    """
}