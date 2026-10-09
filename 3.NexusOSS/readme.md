## NOTE 
NEXUSOSS 
- docker registry 
- helm repository 
- store artifacts ( builds files )
- proxy 
    - npm install 



```bash 
docker exec -it container /bin/sh 

docker exec nexus cat /nexus-data/admin.password
```


-> Configure admin credentials 
-> Configure domain names 
    - domain for nexus ui 
    - domain for container registry


```bash 
sudo apt install nginx -y 


sudo systemctl status nginx 
sudo systemctl start nginx 
sudo systemctl enable nginx 
```
- Linkingg configuration file to the right location of nginx service 
```bash

ln -s <original> <shortcut>

sudo ln -s /home/kk/devoops12-shortcourse-weekend/3.NexusOSS/config/nexus.conf /etc/nginx/conf.d/


sudo ln -s /home/kk/devoops12-shortcourse-weekend/3.NexusOSS/config/registry.conf /etc/nginx/conf.d/



sudo nginx -t 
sudo nginx -s reload 
sudo systemctl restart nginx 


# installing certbot that support nginx module 
sudo apt update
sudo apt install certbot python3-certbot-nginx


# need interaction , choose your domain , yes , no 
sudo certbot --nginx 
sudo certbot --nginx -d domain.name




```
## Testing pushing image in our own repo 

go to Realms setting in Security Section , and Add 
Docker Bearer Token .. 
```bash
docker login -u admin registry.anajak-khmer.site


docker tag spring-app:latest \
    registry.anajak-khmer.site/spring-app:v1.0.1


docker push registry.anajak-khmer.site/spring-app:v1.0.0

docker pull registry.anajak-khmer.site/spring-app:v1.0.0
 
```

## Working with user and role 
- Creating another user in order to use the credential to push images



- docker_admin : user 
- docker_pull : user 

```bash 
docker logout registry.anajak-khmer.site
docker login -u docker_pull registry.anajak-khmer.site

```