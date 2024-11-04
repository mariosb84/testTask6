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

================================================================================================
Endpoints:
==============

Auth:
==============

================================================================================================
Использованные данные, в качестве примера : {id}=1;{phone}=+79111111111;{email}=email@mail.ru;
{name}=User_Test;{password} : 12345678900;{name}=Administrator_Test;{password} : 12345678902;
================================================================================================
/*АВТОРИЗАЦИЯ*/                                                                              (+)

http://localhost:8080/auth/sign-in/

POST

{
"userName" : "userName",
"password" : "password"
}
================================================================================================
/*ДОБАВЛЕНИЕ ПОЛЬЗОВАТЕЛЯ*/                                                                  (+)

http://localhost:8080/auth/sign-up/

POST

{
"userLastName" : "userLastName",
"userName" : "userName",
"userMiddleName" : "userMiddleName",
"userBirthDate" : "1970-01-01",
"password" : "password"
}
================================================================================================
/*ВЫХОД(LOGOUT)*/                                                                             (+)

http://localhost:8080/auth/auth_logout/

POST
================================================================================================


personContacts:
==============


/*НАЙТИ СПИСОК ВСЕХ personContacts*/                                                          (+)

http://localhost:8080/personContacts/

GET
================================================================================================
/*НАЙТИ ПО ID personContacts*/                                                                (+)

http://localhost:8080/personContacts/{id}

GET
================================================================================================
/*Создать personContacts*/                                                                    (+)

http://localhost:8080/personContacts/

POST

{
"email" : "user@mail.ru",
"phone" : "+79111111111"
}
================================================================================================
/*Обновить personContacts*/                                                                    (+)

http://localhost:8080/personContacts/

PUT

{
"email" : "user@mail.ru",
"phone" : "+79111111111"
}
================================================================================================
/*Удалить personContacts*/                                                                    (+)

http://localhost:8080/personContacts/{id}

DELETE
================================================================================================
/*Найти personContacts по email*/                                                             (+)

http://localhost:8080/personContacts/findUserContactsByUserEmail?email=user@mail.ru

GET
================================================================================================
/*Найти personContacts по phone*/                                                             (+)

http://localhost:8080/personContacts/findUserContactsByUserPhone?phone=89111111111

GET
================================================================================================


personPhoto:
==============


/*НАЙТИ СПИСОК ВСЕХ personPhoto*/                                                          (+)

http://localhost:8080/personPhoto/

GET
================================================================================================
/*НАЙТИ ПО ID personPhoto*/                                                                (+)

http://localhost:8080/personPhoto/{id}

GET
================================================================================================
/*Создать personPhoto*/                                                                    (+)

http://localhost:8080/personPhoto/

POST

{
"photo" : "AQID"
}
================================================================================================
/*Обновить personPhoto*/                                                                    (+)

http://localhost:8080/personPhoto/

PUT

{
"photo" : "AQAD"
}
================================================================================================
/*Удалить personPhoto*/                                                                    (+)

http://localhost:8080/personPhoto/{id}

DELETE
================================================================================================
/*Найти personPhoto по Photo*/                                                             (-)

http://localhost:8080/personContacts/findUserPhotoByPhoto?photo=AQID

GET
================================================================================================



person:
==============


/*НАЙТИ СПИСОК ВСЕХ person*/                                                          (+)

http://localhost:8080/person/

GET
================================================================================================
/*НАЙТИ ПО ID person*/                                                                (+)

http://localhost:8080/person/{id}

GET
================================================================================================
/*Создать person*/                                                                    (-)

http://localhost:8080/person/

POST

{
"userLastName" : "userLastName",
"userName" : "userName",
"userMiddleName" : "userMiddleName",
"userBirthDate" : "1970-01-01",
"password" : "password",
"userContacts" : "",
"userPhoto" : "",
"roles" : "",
}
================================================================================================
/*Обновить person*/                                                                    (-)

http://localhost:8080/person/

PUT

{
"photo" : "AQAD"
}
================================================================================================
/*Удалить person*/                                                                    (+)

http://localhost:8080/person/{id}

DELETE
================================================================================================
/*Получить текущего  person*/                                                                    (+)

http://localhost:8080/person/getCurrentUser

GET
================================================================================================
================================================================================================
/*Найти person по имени*/                                                             (+)

http://localhost:8080/person/findByUserName?userName=username

GET
================================================================================================
========================================================================================================


Стек технологий : Java 17; PostgreSQL 14;spring-boot 2.7.14;.
Требования к окружению : Java 17, Maven 3.8, PostgreSQL 14 - 16.

Контакты : mariosb84@mail.ru .

========================================================================================================
