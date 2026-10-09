## NOTE for Docker part II 
Date: 25-July-26


Registry 
- dockerhub ✅ 
- gitlab 
- github
- nexus 


1. Pushing your image to github container registry 
ghcr.io 
```bash 

docker run hello-world 
# pull , run 
docker login ghcr.io -u github-username 
# when use with pipeline 
echo "$PASSWORD" | docker login ghcr.io -u username --password-stdin 

# profile -> settting -> developer settiing -> generate class token 
# password -> token ( package(write : read ) )

# tag image following ghcr.io convention
docker tag hello-world:latest ghcr.io/zoeistad/hello-world:latest 


# docker push to finish it 
docker push ghcr.io/zoeistad/hello-world:latest  
```

- Working gitlab container registry
    - create the repository, store the docker images inside the repo
```bash 
docker login registry.gitlab.com -u keokay
# token (read-registry , write-registry)

# if you build your own docker image 
docker build -t registry.gitlab.com/keokay/demo-gitlab-devops12 .

# since we already have docker image (hello-world)
docker tag hello-world:latest registry.gitlab.com/keokay/demo-gitlab-devops12/hello-world:latest 


docker push registry.gitlab.com/keokay/demo-gitlab-devops12/hello-world:latest

docker pull registry.gitlab.com/keokay/demo-gitlab-devops12/hello-world:latest
```

### Docker Volumes 
=> How to backup your data from the containers 
=> Share data to your containers

1. Docker Volumes ( volume is obj of docker)
2. Bind Mount ( use your server file system to store the data)
3. TMPFS (use RAM store the data temporarily )

- Working with the docker volume object
```bash 
docker volume create database-vol 

# run the postgres with volume attach for backup 
docker run -dp 5433:5432 --name postgres-demo \
    -e POSTGRES_PASSWORD=pass123 \
    -v database-vol:/var/lib/postgresql \
    postgres

docker rm -f postgres-demo


# auto create the volume when the v olume doens't exist 

docker run -dp 5433:5432 --name postgres-demo \
    -e POSTGRES_PASSWORD=pass123 \
    --mount source=database-vol1,target=/var/lib/postgresql \
    postgres


docker run -dp 5432:5432 \
    -e POSTGRES_PASSWORD=pass123 \
    --name postgres-tmpfs \
    --tmpfs /var/lib/postgresql \
    postgres
```
- Working with **bind mount** approaches 
=> build the spring boot project that has file upload features 
=> back up all the files that uploaded to the container by using the bind mount methods.  

- Our updated spring dockerfile
```Dockerfile
ARG GRADLE_VERSION=7.6
FROM gradle:${GRADLE_VERSION} AS builder
WORKDIR /app
# Copy necessary directory
COPY build.gradle ./build.gradle
COPY settings.gradle ./settings.gradle
COPY src ./src
# COPY . . 
RUN gradle build -x test  
# -x test : means skip the test
# serve
FROM eclipse-temurin:17-jdk
ARG PORT=8080
ENV PORT=${PORT}
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar  app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","app.jar","--server.port=${PORT}"]
```

```bash 
docker build -t spring-app -f dev.Dockerfile . 

docker run -dp 8080:8080 --name spring-cont \
    --mount type=bind,source=/home/kk/source_projects/file-storage,target=/app/filestorage/images \
    spring-app
```



### PORTAINER 
Usage: UI for the docker run on web service 

```bash 
# Create a volume for Portainer data persistence
docker volume create portainer_data

# Run the Portainer Server container
docker run -d -p 8000:8000 -p 9443:9443 \
    --name portainer --restart=always \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v portainer_data:/data \
    portainer/portainer-ce:latest

# Access it in your browser by 
# https://localhost:9443

# to get the token for setting up the portainer account
docker logs portainer 2>&1 | grep -i "token"

```
## Docker network 


Bridge 
```bash 
docker run -dp 3000:3000
docker run -dp 3001:3000
```
- you need to create the custom bridge network in order to 
    - communicate between each service by using the name of the service or container name 

- host network: 
No need to expose your container port , 
service will be expose through the host network , by the port of your container 
```bash 
docker run -d --name nginx-cont \
    --network host nginx:latest
docker run -d --name react-cont \
    --network host demo-react:latest
```



- Bridge network (customed)
```bash 
# this is called customed brige network 
docker network create -d bridge demo-network 

# create container1 
# --rm auto remove when exit the container 
docker run -it --rm --name container1 \
    --network demo-network \
    busybox:latest 

# open new terminal 
docker run -it --name container2 \
    busybox:latest 

# on Container1 terminal 
ping container1 # it should work 
ping container2 # it will show bad address 


# to fix this, we add container2 to the same network as container1

docker network connect demo-network container2
# ping again 
```

