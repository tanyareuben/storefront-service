FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk

WORKDIR /app

COPY target/storefront-0.0.1-SNAPSHOT.jar /app/app.jar

# Copy the wait-for-it script
COPY wait-for-it.sh /app/

# Set execute permissions for the script
RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "app/app.jar"]
# Command to start the application with wait-for-it
CMD ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]
