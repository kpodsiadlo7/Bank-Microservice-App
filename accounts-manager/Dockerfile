FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY .mvn .mvn

RUN mvn -f /home/app/pom.xml clean install
FROM openjdk:17
FROM tomcat:latest

COPY --from=build /home/app/target/accounts-manager-0.0.1-SNAPSHOT.jar /usr/local/lib/accounts-manager.jar
EXPOSE 8010
ENTRYPOINT ["sh", "-c", "java -jar /usr/local/lib/accounts-manager.jar"]