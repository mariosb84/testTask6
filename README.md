Тестовое задание:

![img_1.png](img_1.png)

### Проект "testTask3".

### Для запуска проекта :

1. Создать БД командой `create database userService;` ;

2. В файле "application.properties" вам необходимо внести ваши данные:
      (как минимум : пароль для БД)
- spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/userService
- spring.datasource.username=postgres
- spring.datasource.password='passwordForUserService'(пароль для БД)

3. В файле "liquibase.properties" вам также необходимо внести ваши данные:
   (как минимум : пароль для БД)
- changeLogFile=src/main/resources/liquibase-changeLog.xm
- url=jdbc:postgresql://127.0.0.1:5432/itemService
- username=postgres
- password='passwordForItemService'(пароль для БД) ;

4. Запустить приложение командой `mvn spring-boot:run;` ;


5. Чтобы выполнить какие - либо действия, необходимо использовать
следующие данные для авторизации:

/auth/sign-in/

POST

{
"userName" : "User_Test",
"password" : "12345678900"
}

{
"userName" : "Administrator_Test",
"password" : "12345678902"
} ;


========================================================================================================


Стек технологий : Java 17; PostgreSQL 14;spring-boot 2.7.14;.
Требования к окружению : Java 17, Maven 3.8, PostgreSQL 14 - 16.

Контакты : mariosb84@mail.ru .

========================================================================================================
