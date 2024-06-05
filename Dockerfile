
FROM maven:latest AS build

# Копируем исходный код проекта в рабочую директорию контейнера
COPY . /usr/src/app

# Устанавливаем рабочую директорию
WORKDIR /usr/src/app

# Собираем проект без выполнения тестов
RUN mvn clean package -DskipTests

# Используем образ с Java для запуска собранного приложения
FROM openjdk:17

# Копируем JAR файл из предыдущего этапа сборки в контейнер
COPY --from=build /usr/src/app/target/new-mongo-project-0.0.1-SNAPSHOT.jar /usr/app/app.jar

# Устанавливаем рабочую директорию
WORKDIR /usr/app

# Запускаем JAR файл
CMD ["java", "-jar", "app.jar"]