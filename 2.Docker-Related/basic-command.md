## NOTE 
```bash 
sudo systemctl status docker 
sudo systemctl stop docker 
sudo systemctl enable docker 
sudo systemctl disable docker 
sudo service docker status 

docker ps # list running containers 
docker ps -a # list all containers 

docker container ls 
docker container ls -a 
docker rm <container-id> | <container-name>
docker rm -f <container-id>  # force 

docker stop <container-id> 
docker rm <container-id> 

docker version 
docker -v

docker pull nginx 
docker image ls # list all the image you download 
docker images 
docker rmi nginx # remove nginx image 

# image -> container 
# dockerfile -> create image -> container 
# one image -> can create multiple containers 

# volume used to backup data 
docker volume ls 
docker network ls 
```

### Starting a datbaase service (postgres )

```bash 
# environment variables 
docker run -dp 5432:5432 \
    --name db-cont \
    -e POSTGRES_PASSWORD=pass123 \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_DB=postgres \
    postgres:latest 

docker logs db-cont 
docker logs -f db-cont # --follow 

docker volume ls # you will see a new vol 
# vol is auto created , because of the postgres image

docker rm -f $(docker ps -aq )
docker network prune 
docker container prune 
docker volume prune 
docker image prune 
```


+ **naming Dockerfile** 
- Dockerfile
- dev.Dockerfile 
- prod.Dockerfile 

```bash 
# if you file is: Dockerfile
docker build -t demo-react . 
docker build -t demo-react:v1.0.0 . 
# . refer context , location of the dockerfile
docker build -t demo-react -f dev.Dockerfile . 

docker images 
docker run -dp 3000:3000  \
    --name demo-cont demo-react

# log into the container 
docker exec -it demo-cont /bin/sh 
# /bin/bash /bin/sh /bin/ash 


# cd reactjs-devops8-template
docker build -t prod-react -f prod.Dockerfile . 
docker run -dp 3001:80 --name prod-cont prod-react 


# image name: prod-react 
# PAT -> Personal Access TOken 
docker login -u <your-username> 

# docker tag -> rename your image in order to push 
# image name = username/imagename:version 

docker tag prod-react lyvanna544/devops12-prod-reactjs:v1.0.0 
docker push lyvanna544/devops12-prod-reactjs:v1.0.0
```

Pause / Unpaused 
Stop / Start 

Resource Allocation 
- ghcr.io 
- registry.gitlab.com 
- nexusoss -> install nexus , customize domain 
- dockerhub 
- quay.io 




Dockerfile , Docker Image , Container 

Docker images are pulled froom docker registry (Dockerhub)
ex. postgres , nginx, redis, keycloak

Dockerfile : text file contains the instruction for building the image: 

- reactjs 
- springboot

1. foundation/fundamental instruction 
from gradle,maven
2. configuration instruction 
steps for building the project 
-reactjs: copy package.json, npm install , copy source code , run npm run build 
- spring : copy gralde config, install dependencies , build to file jar,  
3. execution instruction
- cmd , entrypoint 

