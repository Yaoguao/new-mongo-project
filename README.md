# Diary

Это веб приложение представляет собой ее backend часть, для системы: добавления, удаления и изменения учеников, учителей и предметов.

## Запуск проекта

Чтобы запустить проект, выполните следующий шаг:

1. Запустите скрипт:

    ```sh
    ./build.sh
    ```

## Использование

После успешного запуска системы, вы можете обраться по hhtp запросу:

```HTTP
POST /api/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cookie: JSESSIONID=A7F84576EBAFD95C7BAADCEED065D3DD
Content-Length: 63

{
    "username": "adminDiary",
    "password": "password"
}
```
Получив в ответе, в body, токен для авторизации. Вы можете например получить список всех студентов.

```HTTP
GET /api/students HTTP/1.1
Host: localhost:8080
Authorization: bd087384-9818-43b9-bbd2-794acfc6974f
Cookie: JSESSIONID=A7F84576EBAFD95C7BAADCEED065D3DD
```

На выходе мы получил JSON:

```JSON
[
    {
        "id": "6653a40280aa77729c0c70f8",
        "name": "Pasha",
        "email": "kii@gmail.com"
    },
    {
        "id": "6654f1a9b7d8890cee94313d",
        "name": "TestStudent1",
        "email": "test1@gmail.com"
    },
    {
        "id": "665745dd247729488e58f83e",
        "name": "testStudent2",
        "email": "test2@gmail.com"
    }
]
```

Там вы сможете произвести полный набор действий для учеников, учителем и предметов.

## Заключение

Вы так же можете посмотреть исходный код [frontend](https://github.com/Yaoguao/vue-mongo-project) часть приложения. где так же его скачать и запустить.
