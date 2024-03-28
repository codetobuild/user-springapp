FROM openjdk:17-jdk-alpine

COPY target/userapp-0.0.1-SNAPSHOT.jar userapp-0.0.1-SNAPSHOT.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","/userapp-0.0.1-SNAPSHOT.jar"]
