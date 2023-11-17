## Running the Storefront APIs in your local laptop

You need to have Docker installed locally on your Laptop.

### Installing Docker Desktop

#### If you have a MAC

Head to the docker desktop [download](https://docs.docker.com/desktop/install/mac-install/) page.
Make sure you download the right version of the installable for your MAC and follow the installation instructions.

Download the Apple Silicon installable if you have a M1,M2 or M3 processor - Check by clicking the 'Apple Menu' on Top Left and select 'About this Mac'

#### If you have a Windows laptop

Head to the docker desktop [download](https://docs.docker.com/desktop/install/windows-install/) and follow the instructions to download and install Dockere Desktop


#### Running the Storefront Services:

* Clone the Github repo

```
git clone 

```

* Change directory to the root folder of the project.

* Make sure you see a docker-compose.yaml in the folder

* Run the following command

```
docker compose up
```

You will see a bunch of logs in your window, and finally you will see something like

```
Started StorefrontApplication in 13.578 seconds (process running for 14.708)
```

Then you can head over to [here](http://localhost:8080/swagger-ui/index.html) to check the APIs are available. You can actually start using the APIs as well.

##### Note:
This will download the required docker images and run the app. First time it might take few minutes, but on subsequent times it will be pretty quick


If you want to conenct to the Datbase and see the Storefront tables and contents, you can download MySQL workbench (search google for download links for Mac and Windows)


#### Building and Running the Storefront APIs locally

If you  want to build and run the code yourself follow the instructions [here](BuildAndRunLocally.md)
