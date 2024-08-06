FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Recipe_Bank.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
