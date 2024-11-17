# Use an official JDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/auth_microservice.jar /app/auth_microservice.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/auth_microservice.jar"]

# Expose the port the application runs on
EXPOSE 8080
