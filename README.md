# github-webservices
# kubernetics-web-services

<!-- 
docker  run -d -p9296:9296  -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name  config-server  46e607fdda6a

 docker  run -d -p9090:9090 -e CONFIG_SERVER_URL=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name  cloudgateway  c83213734902

echo "# kubernetics-web-services" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/vistait2018/kubernetics-web-services.git
git push -u origin main

docker compose -f docker-compose.yml up   // this start the docker images sequentially
 
docker compose -f docker-compose.yml up -d  //  this start the docker images sequentially in a detach mode

docker compose -f docker-compose.yml up  // this stop the container and remove them sequestially


images: 'perspective1974/cloudgateway:0.0.1'
    container_name: 'cloudgateway'
    ports:
      - '9090:9090'
    depends_on:
      configserver:
        condition: service_healthy
    environment:
      - EUREKA_SERVER_ADDRESS=http://serviceregistry/eureka
      - CONFIG_SERVER_URL=configserver


-Djib.from.auth.username	Username for base image registry.
-Djib.from.auth.password	Password for base image registry.

kubectl create secret docker-registry regcred --docker-server=https://docker.pkg.github.com --docker-email=jidedorcas@gmail.com  docker-password=Java20091@ --docker-server=perspective197

 docker login DOCKER_REGISTRY_SERVER --username=perspective1974 --password=Java20091@ --email=jidedorcas@gmail.com'
 minikube start

 kubectl create secret docker-registry my-secret --docker-server=DOCKER_REGISTRY_SERVER --docker-username=perspective1974  --docker-password=Java20091@ --docker-email=jidedorcas@gmail.com
-Djib.to.auth.username	Username for target image registry.
-Djib.to.auth.password	Password for target image registry. 
# to create your pod use template or get relevant template
# See to it the you are logged in into docker
# if you are not logged in 
# kubectl create secret docker-registry regcred --docker-server=https://docker.pkg.github.com --docker-email=*****  docker-password=***** --docker-server=*****
# then you can log in using docker login
# deploy using kubectl apply -f .\FILENAME
#  to create a name space use kubectl create namespace my_namespace
# to attach it use kubectl apply -f .\FILENAME -n my_namespace
#  note that when to retieve pods in your namespace use
#  kubectl get namespace , kubectl get all -n my-namespace




-->
