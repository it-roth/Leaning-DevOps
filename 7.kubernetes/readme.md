## NOTE

```bash 
sudo kubectl get node 
sudo kubectl get node -o wide 

# pod -> inside a pod there are containers 
sudo kubectl get pod -A # all namespace 
```

* Run kubectl without typing sudo all the time 
```bash
# Create the local configuration directory
mkdir -p ~/.kube
# Copy the admin configuration file locally
sudo cp -i /etc/kubernetes/admin.conf ~/.kube/config
# Give your standard user account complete ownership of the file
sudo chown $(id -u):$(id -g) ~/.kube/config

```

*** 
### Working with kubenernets object. 
There are two ways to work with k8s obj 
- Imperative (adhocs )
- Declarative (yaml)
1. Pod 
```bash 
# imperative approach 
kubectl get pod 
kubectl run my-nginx --image=nginx:alpine --restart=Never
```
- Declarative approach 
```bash 
kubectl apply -f demo-pod.yaml 
kubectl delete -f demo-pod.yaml 

kubectl get pod -o wide 
kubectl logs -f pod-name 
# execute into the container 
kubectl exec -it pod-name -- bash 
kubectl describe pod/pod-name 

kubectl delete pod/podname 
```
- if you want to edit any object in kubernete s
```bash 
kubectl edit deploy/nginx-dpl 
# scale up or down 
kubectl scale deployment nginx-dpl --replicas=2
```

```bash 
# default editor is vim 
# if you want to switch to nano 
KUBE_EDITOR="nano" kubectl edit svc <service-name>
```

- Edit the service config in one command. 
```bash 
kubectl get svc 
kubectl describe svc myapp-svc
kubectl edit svc myapp-svc 

kubectl patch svc myapp-svc -p '{"spec":{"selector":{"replicas":"green"}}}'




```