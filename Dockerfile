# Build stage
FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copy the project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Final stage
FROM openjdk:17-jdk

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/storefront-0.0.1-SNAPSHOT.jar /app/app.jar

# Copy the wait-for-it script
COPY wait-for-it.sh /app/

# Set execute permissions for the script
RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080

# Command to start the application with wait-for-it
CMD ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]
