# Use a slim JDK 21 image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built Spring Boot JAR from your target/ directory to /app/teryaq.jar in the container
COPY target/edusphere-0.0.1-SNAPSHOT.jar /app/edusphere.jar

# Expose the port your Spring Boot application listens on (3000)
EXPOSE 13001

# Define the command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/edusphere.jar"]