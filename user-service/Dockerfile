# Use a lightweight JDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file into the image
COPY target/user-service-*.jar app.jar

# Set the startup command
ENTRYPOINT ["java", "-jar", "app.jar"]
