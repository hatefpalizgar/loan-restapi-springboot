# 

# README

## Swedbank Loan RESTful API 



#### TECH-STACK: 

-   [ ] Spring Boot (Java 8) 
-   [ ] Gradle 
-   [ ] MongoDB 
-   [ ] Docker(with GitHub-linked autobuild workflow )
-   [ ] JUnit and Mockito for Unit Testing
-   [ ] Spring Mockito(and MockMvc) -  for API Testing
-   [ ] Jacoco Gradle Plugin for Test Coverage Reporting



#### Resource files and repositories

git repository is found at:

https://github.com/hatefpalizgar/loan-restapi-springboot.git

simply use `git clone ` command to get the code on your local machine. 

Then use `cd loan-restapi-springboot` to change path to root.

`Docker` image for the `Spring Boot` app is located at following registry:

https://hub.docker.com/repository/docker/hatefpalizgar/sw-restapi-springboot-repo

Use `docker pull hatefpalizgar/sw-restapi-springboot-repo:latest` to get the image. 

--use this command if you already have mongoDb installed locally. Just change the datasource attributes inside `application.properties` file.

If you don't have `MongoDB` installed locally then follow along:

In order to link this container with `MongoDB` image to prepare the persistence layer,

please use `docker-compose up`. By executing this command, both application and mongoDb containers would be deployed automatically while linked, and **app is up and running**. 

==NB: The Docker repository is linked to the github repo- e.g., any push into codebase will be automatically tested and built as a docker image into Dockerhub.==

I have also implemented the CI/CD using `Jenkins` in Dev environment. Thus, in development environment I was able to use `Jenkins` to automate the whole code build process and image containerization to `Docker`.

For generating test coverages, I used `Jacoco` plugin for Gradle. 

By default, a HTML report is generated at: `*$buildDir*/reports/test`.

Coverage report is generated automatically during each build lifecycle as a task called `jacocoTestReport`. 

![Task](https://github.com/hatefpalizgar/loan-restapi-springboot/blob/master/Task.png)





