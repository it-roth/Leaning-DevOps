
- https://www.jenkins.io/doc/book/installing/linux/
```bash
sudo apt update
sudo apt install fontconfig openjdk-21-jre
java -version



sudo wget -O /etc/apt/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2026.key
echo "deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt update
sudo apt install jenkins


sudo ln -s /home/kk/devoops12-shortcourse-weekend/4.Jenkins/jenkins.conf /etc/nginx/conf.d/

```


+ Download the agent to run 
```bash 
# run agent normal mode 
curl -sO https://jenkins.anajak-khmer.site/jnlpJars/agent.jar;java -jar agent.jar -url https://jenkins.anajak-khmer.site/ -secret your-secret -name slave1 -webSocket -workDir "/home/kk/jenkins"


# run in it backgroup 
curl -sO https://jenkins.anajak-khmer.site/jnlpJars/agent.jar;java -jar agent.jar -url https://jenkins.anajak-khmer.site/ -secret your-secret -name slave1 -webSocket -workDir "/home/kk/jenkins" &
```


```bash

curl https://jenkeo:token@jenkins.anajak-khmer.site/job/docker-pipeline/build?token=12345

```

 or /buildWithParameters?token=TOKEN_NAME