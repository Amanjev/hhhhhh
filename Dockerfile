# Use an official OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy project files
COPY . .

# Give Maven Wrapper permission to run
RUN chmod +x mvnw

# Build the project (skip tests)
RUN ./mvnw clean install -DskipTests

# Run the Spring Boot JAR
CMD ["java", "-jar", "target/*.jar"]
