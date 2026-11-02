# Multi-stage build for the ms-cpf-validation-topic microservice
# Stage 1: Build the application

FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /build/target/hexagonal-0.0.1-SNAPSHOT.jar /app/hexagonal.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "/app/hexagonal.jar"]