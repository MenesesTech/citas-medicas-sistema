FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app.jar"]