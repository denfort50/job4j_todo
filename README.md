# job4j_todo

## Описание проекта
Данный проект представляет собой реализацию сайта для ведения задач.  
Реализованный функционал:
* 3 списка задач: все задачи, новые задачи, выполненные задачи;
* если задача не выполнена, то её можно отредактировать, отметить выполненной или удалить;
* если задача выполнена, то её можно только удалить;
* если нужно удалить все задачи разом, то для этого предусмотрена кнопка на главной странице списка.

## Стек технологий
* Java 17
* PostgreSQL 14
* Apache Maven 3.8.5
* Spring Boot 2.7.3
* Liquibase 4.15.0
* Hibernate 5.6.11.Final;
* Log4j 1.2.17
* Lombok 1.18.24
* JUnit 5.8.2
* Mockito 4.8.0
* Checkstyle 8.29
* Thymeleaf 3.0.15

## Требуемое окружение для запуска проекта
* Браузер
* JDK 17
* Apache Maven 3.8
* PostgreSQL 14

## Инструкция по запуску проекта
1) Скачать и разархивировать проект
2) В PostgreSQL создать базу данных cinema (url = `jdbc:postgresql://127.0.0.1:5432/todo`)
3) Открыть командную строку и перейти в папку с проектом, например `cd c:\projects\job4j_todo`
4) Выполнить команду `mvn install`
5) Перейти в папку target командой `cd target`
6) Выполнить команду `java -jar job4j_todo-1.0.jar`
7) Перейти по ссылке `http://localhost:8081/tasks`

## Взаимодействие с приложением

### При открытии сайта попадаем на страницу для авторизации
![img.png](img/loginPage.png)

### Если учетная запись не найдена, то будет выведено сообщение
![img.png](img/incorrectLogin.png)

### Если учетной записи ещё нет, то необходимо зарегистрироваться
![img.png](img/regPage.png)

### Если регистрация прошла успешно, то будет выведено сообщение
![img.png](img/regSuccess.png)

### Если регистрация прошла неуспешно, то будет выведено сообщение
![img.png](img/regFail.png)

### После успешной аутентификации попадаем на страницу со списком всех задач
![img.png](img/allTasks.png)

### При желании можно посмотреть новые / выполненные задачи отдельными списками
#### Новые задачи
![img.png](img/newTasks.png)
#### Выполненные задачи
![img.png](img/completedTasks.png)

### Страница с формой добавления задачи
![img.png](img/addTask.png)

### Страница с формой редактирования задачи
![img.png](img/modifyTask.png)

### Если задача не выполнена, то на странице с подробной информацией 3 кнопки: "Завершить", "Изменить", "Удалить"
![img.png](img/notCompletedTaskInfo.png)

### Если задача выполнена, то на странице с подробной информацией только кнопка "Удалить"
![img.png](img/completedTaskInfo.png)

## Контакты для связи
[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/kalchenko_denis)&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:denfort50@yandex.ru)&nbsp;&nbsp;
