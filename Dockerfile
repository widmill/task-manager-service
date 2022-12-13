FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY build/libs/task-manager-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","task-manager-service-0.0.1-SNAPSHOT.jar"]