# Development Stage
FROM maven:3.8.7-amazoncorretto-17 AS development
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src

# Add Spring Boot DevTools for hot reload
RUN mvn clean compile

EXPOSE 8080 35729
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Dspring.profiles.active=dev"]

# Build Stage
FROM maven:3.8.7-amazoncorretto-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Production Stage
FROM amazoncorretto:17 AS production

ENV SPRING_PROFILES_ACTIVE=prod

WORKDIR /app
COPY --from=build /build/target/*.jar /app/Relief-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "Relief-app.jar"]