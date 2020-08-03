#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM adoptopenjdk:11-jre-hotspot

COPY --from=build /home/app/target/*.jar /app.jar
EXPOSE 8090

ENTRYPOINT ["java","-jar","/app.jar"]
