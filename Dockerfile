FROM openjdk:17


COPY target/new-mongo-project-0.0.1-SNAPSHOT.jar /app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]