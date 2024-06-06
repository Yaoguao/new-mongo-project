FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /usr/src/app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /usr/app

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar

CMD ["java", "-jar", "app.jar"]