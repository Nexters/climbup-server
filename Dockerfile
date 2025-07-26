# Build stage
FROM gradle:8.14.3-jdk17 AS build
WORKDIR /app

# Copy only dependency files first for better caching
COPY build.gradle settings.gradle ./

# Download dependencies (this layer will be cached if dependencies don't change)
RUN gradle dependencies --no-daemon

# Copy source code
COPY src ./src

# Build application (skip tests for faster builds, run them separately in CI)
RUN gradle clean build -x test --no-daemon --parallel

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*SNAPSHOT.jar project.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "project.jar"]