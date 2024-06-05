# Используем образ nginx в качестве базового образа
FROM nginx:latest

# Копируем собранное приложение Vue в рабочую директорию Nginx
COPY ../dist/ /usr/share/nginx/html

# Открываем порт 80 для доступа к Nginx
EXPOSE 80

# Команда запуска Nginx внутри контейнера
CMD ["nginx", "-g", "daemon off;"]