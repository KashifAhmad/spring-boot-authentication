# Use an official JDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/inventory_microservice.jar /app/inventory_microservice.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/inventory_service.jar"]

# Expose the port the application runs on
EXPOSE 8083
