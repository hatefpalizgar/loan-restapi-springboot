FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar LoanapiApplication.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/LoanapiApplication.jar"]