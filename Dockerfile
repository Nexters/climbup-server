# Build stage
FROM gradle:8.14.3-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle clean build -x test --no-daemon

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*SNAPSHOT.jar project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "project.jar"]