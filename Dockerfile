FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk

WORKDIR /app

COPY --from=builder /app/target/storefront-0.0.1-SNAPSHOT.jar app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app/app.jar"]
