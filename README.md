# Running the Storefront APIs

### MySQL:

The project uses MySQL database as the backend to store the Data. There are two ways you can install MySQL locally

#### 1. Using Docker:
There is a docker compose file provided that will run the MySQL in your machine. No need to install MySQL in your machine
But you need to have 'Docker Desktop' installed in your machine. See www.docker.com

Once you have docker desktop, run the app as any other app in your machine. Once docker is running ( you can keep it running no need to shut it down), you can issue the following command in your Terminal window

```
docker compose up

```

This will bring up the MySQL docker image, setup the 'storefront' db for the storefront backend to connect

#### 2. Downloading and Installing MySQL locally in your computer. Follow the download link below
```
https://dev.mysql.com/downloads/mysql/

```
You have to be careful which installable you download and install on your mac. If you have an M1 Mac dowload and install the 'macOS 13 (ARM, 64-bit), DMG Archive'. If you have an Intel based Mac, download and install the 'macOS 13 (x86, 64-bit), DMG Archive'
Then run MySQL locally.

#### Download and Run MySQL Workbench from the following location

```
https://www.mysql.com/products/workbench/

```

### Setting up the Storefront APIs locally in your Machine

#### Setup JAVA and Maven

You need JAVA 17 or above installed in your machine. Mac usually comes with JAVA.
Check the JAVA version. On your Mac terminal, type the following

```
java -version

```

If the Java version displayed is less than 17, please download the JAVA 17 or later from the JAVA download site
Make sure you download the installable for ARM (for Mac Silicon) or for Intel Mac

Install maven if you don't have already isntall it 
In Mac, run the following command in the terminal

```
brew install mvn

```
If brew is not installed, search google how to install brew

First git clone the Repo. You can use the GitHub desktop or just the Mac terminal to clone

```
git clone 

```

Open command terminal and change directory to the root folder where you cloned the Repo

Then run the following command


``` 
mvn clean install

```

followed by

``` 
mvn wrapper:wrapper

```
The above commands you need to RUN every time you sync the code from the repository to your local machine.


Now run your SpringBoot java web applications for your REST APIS

To RUN your spring boot application, run the following command, every time you need to run the app

``` 
./mvnw spring-boot:run

```

* Once you run the application, the API Documentation can be accessed [here](http://localhost:8080/swagger-ui/index.html)

* and your REACT app cna connect to the APIs at https://localhost:8080. For example if you want to login send a POST command with the email and password to https://localhost:8080/users/login

* for the rest of the APIs available look at the documentation [here](http://localhost:8080/swagger-ui/index.html)

### Guides

The APIs are built using Spring Boot. The following Guides will help you understand how to build Services with springboot
Using JPA as the Data Access mechanism. The data base used is MySQL


* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

