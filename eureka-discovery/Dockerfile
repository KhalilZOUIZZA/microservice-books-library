# Stage 1: Build the backend application
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Create and set the working directory
WORKDIR /workspace

# Copy the Maven project files into the container
COPY pom.xml .
COPY src ./src

# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Run the backend application
FROM openjdk:17-jdk-slim

# Copy the built JAR file from the build stage
COPY --from=build /workspace/target/*.jar app.jar

# Expose the application's port
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]