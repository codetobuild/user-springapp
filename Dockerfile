FROM openjdk:22-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/app.jar"]
