mvn clean install -DskipTests
mvn wrapper:wrapper
docker build -t storefront-app:v4.0 -f Dockerfile .
docker tag storefront-app:v4.0 reubensjohn/storefront-app:v4.0
docker tag storefront-app:v4.0 reubensjohn/storefront-app:latest
docker login
docker push reubensjohn/storefront-app:v4.0
docker push reubensjohn/storefront-app:latest

