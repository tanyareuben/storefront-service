# Use the official MySQL image as the base image
FROM mysql:latest

# Set environment variables for MySQL
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=storefront

# Copy the initialization script into the container
COPY docker-entrypoint-initdb.d/init.sql /docker-entrypoint-initdb.d/
