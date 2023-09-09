FROM openjdk:17

COPY target/config-server-0.0.1-SNAPSHOT.jar configserver.jar

ENTRYPOINT ["java","-jar","/configserver.jar"]

EXPOSE 8761