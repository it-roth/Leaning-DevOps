

Go to @botfather

/start 
/newbot 

add username 

https://api.telegram.org/bot<token-here>/getUpdates
- to send message from jenkins to telgram
```bash 
CHAT_ID=your-chat-id
TOKEN=your-tele-token
```

- To send message from jenkins pipeline 
```bash 
sh """
    curl -s -X POST "https://api.telegram.org/bot${token}/sendMessage" -d chat_id="${chatId}" -d parse_mode="Markdown"  -d text="${message}"

    """
```