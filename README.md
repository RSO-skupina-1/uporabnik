# RSO: uporabnik destinacij microservice

## Prerequisites

```bash
docker run -d --name pg-uporabnik -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=uporabnik -p 5433:5432 postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar uporabnik-api-1.0.0-SNAPSHOT.jar
```
Available at: localhost:8081/v1/images

## Docker commands
```bash
docker build -t uporabnik .   
docker images
docker run -p 8081:8081 uporabnik    
docker tag uporabnik rso/uporabnik   
docker push rso/uporabnik  
```
```bash
docker network ls  
docker network rm rso
docker network create rso
docker run -d --name pg-uporabnik -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=uporabnik -p 5433:5433 --network rso postgres:13
docker inspect pg-uporabnik
docker run -p 8081:8081 --network rso -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-uporabnik:5433/uporabnik rso/uporabnik
```

## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl create -f uporabnik-deployment.yaml 
kubectl apply -f uporabnik-deployment.yaml 
kubectl get services 
kubectl get deployments
kubectl get pods
kubectl logs uporabnik-deployment-6f59c5d96c-rjz46
kubectl delete pod uporabnik-deployment-6f59c5d96c-rjz46
```

Kubernetes secrets configuration: https://kubernetes.io/docs/tasks/configmap-secret/managing-secret-using-kubectl/

```bash
kubectl create secret generic pg-pass --from-literal=password=mypassword
kubectl get secrets
kubectl describe secret pg-pass
```


