# Use Amazon Corretto for JDK 17
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# Copy the Spring Boot application JAR file
COPY target/*.jar app.jar

# Expose port 8080 for the Spring Boot app
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
