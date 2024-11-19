# Use an official JDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/prod_mng_ms.jar /app/prod_mng_ms.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/prod_mng_ms.jar"]

# Expose the port the application runs on
EXPOSE 8082
