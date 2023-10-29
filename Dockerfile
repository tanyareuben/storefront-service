FROM openjdk:17-jdk

COPY target/storefront-0.0.1-SNAPSHOT.jar app/app.jar
COPY create-schema.sql .

EXPOSE 8080

CMD ["java", "-jar", "app/app.jar"]
