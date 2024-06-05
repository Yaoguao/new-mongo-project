FROM openjdk:17

# Копируем JAR файл из предыдущего этапа сборки в контейнер
COPY app.jar /usr/app/app.jar

# Устанавливаем рабочую директорию
WORKDIR /usr/app

# Запускаем JAR файл
CMD ["java", "-jar", "app.jar"]