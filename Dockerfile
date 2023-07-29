#base image: linux alpine os with open jdk 8
#Dockerfile - cmd vao folder chua Dockerfile
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
#copy jar from local into docker image
COPY ./target/KITS-BACKEND-SBSC-0.0.1-SNAPSHOT.jar app.jar
#command line to run jar
ENTRYPOINT ["java","-jar","/app.jar"]
