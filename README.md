# Running the Storefront APIs

You need JAVA 17 or above installed in your machine

Install maven if you dont have already
In Mac use command

```
brew install mvn

```

First clone the Repo
Open command terminal and chage directory to the root folder where you cloned the Repo

Then run the following command

``` 
mvn clean install

```

followed by

``` 
mvn wrapper:wrapper

```
The above commands you need to RUN only the First Time

To RUN your spring boot application, run the following command, every time you need to run the app

``` 
./mvnw spring-boot:run

```

* Once you run the application, the API Documentation can be accessed [here](http://localhost:8080/swagger-ui/index.html)

### Guides

The APIs are built using Spring Boot. The following Guides will help you understand how to build Services with springboot
Using JPA as the Data Access mechanism. The data base used is MySQL


* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

