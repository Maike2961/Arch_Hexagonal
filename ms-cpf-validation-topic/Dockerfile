# Multi-stage build for the ms-cpf-validation-topic microservice
# Stage 1: Build the application

FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /build/target/validation-cpf-0.0.1-SNAPSHOT.jar /app/ms-cpf-validation-topic.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/ms-cpf-validation-topic.jar"]

