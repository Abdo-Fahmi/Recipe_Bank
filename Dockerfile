FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Recipe_Bank.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
