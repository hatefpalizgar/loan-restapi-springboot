FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar LoanapiApplication.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongodb:27017/LoanDB","-Djava.security.egd=file:/dev/./urandom","-jar","/LoanapiApplication.jar"]