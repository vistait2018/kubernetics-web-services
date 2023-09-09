FROM openjdk:17

COPY target/cloud-gateway-0.0.1-SNAPSHOT.jar cloudgateway.jar

ENTRYPOINT ["java","-jar","/cloudgateway.jar"]

EXPOSE 9090