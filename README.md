# Проект DVD Discs Exchange

Для установки проекта необходимо выполнить следующие действия:
1. Скачать проект из репозитория
2. Из командной строки или из среды разработки запустить mvn clean install

Для запуска проекта необходимо выполнить файл run.bat(run.sh) из папки targer. Либо из той же папки в командной строке выполнить команду java -jar dvd-1.0-SNAPSHOT.jar --server.port=8080 

--server.port - необязательный аргумент для настройки порта сервера с приложением

После запуска необходимо дождаться загрузки сервера с приложением, строка в консоли "Started DvdApp in ... seconds" свидетельствует о запуске приложения.
Для начала работы с приложением открыть браузер с URL http://localhost:8080
После авторизации (добавлены три пользователя: admin, borsch, ivanov с паролями password) становятся доступны 6 разделов
1. Dashboard - страница с дисками пользователя (собственные и взятые у других пользователей)
2. Add Disc - страница для добавления новых дисков с возможностью поиска фильмов в базе https://www.themoviedb.org/ и использования данных фильма для заполнения информации о диске
3. Free Discs - страница свободных дисков всех пользователей
4. Using Discs - страница дисков, взятых текущим пользователем у других.
5. Given Discs - страница дисков текущего пользователя, взятых другими.
6. All Discs - страница с дисками всех пользователей

Действия с дисками доступные пользователю
1. Save - создать/сохранить изменения (на странице добавления)
2. Remove - удалить (доступно только владельцу диска)
3. Take - взять диск (доступно только для свободных дисков)
4. Get Back - вернуть диск (доступно только для дисков, взятых текущим пользователем)

## Структура проекта

# client - проект с frontend на Angular 

Компоненты:
1. disc-detail - страница для создания/редактирования информации о диске
2. discs - страница представления (списков) дисков
3. login - страница авторизации
4. movie-search - компонент для поиска фильмов на странице создания/редактирования информации о диске

Сервисы:
1. auth-guard - сервис проверки аутентификации текущего пользователя (используется для проверки возможности перехода на страницы приложения) 
2. authentication - сервис аутентификации посылает информацию для базовой аутентификации 
3. disc - сервис для выполнения различных действий с дисками 
4. movie - сервис поиска фмльмов в базе https://www.themoviedb.org/
5. user - сервис для хранения информации о текущем пользователе 

Из примечательного
auth.interceptor - компонент для добавляющий во все запросы, кроме запросов к базе фильмов, заголовок 'X-Requested-With': 'XMLHttpRequest'(необходим для аутентификации)

# backend - Spring Boot проект 

Конфигурация (пакет ru.borsch.test.config):
1. AdditionalWebConfig - конфигурация CORS, позволяющая обращаться к серверу из любого "места" с любым запросом. Используется для удобства разработки frontend (можно убрать, но решил оставить для нагядности)
2. JpaConfiguration - конфигурация для настройки источника данных Hickari (где-то вычитал, что самый быстрый для JPA в Spring)
3. SecurityConfig - конфигурация Spring Security для настройки аутентификации и доступа к ресурсам

Контроллеры (пакет ru.borsch.test.controller):
1. AppController - контрллер мапинга запросов приложения
2. RestApiController - контроллер мапинга REST-запросов

Модели/сущности БД (пакет ru.borsch.test.model):
1. Disc - диск. Из примечательного - добавил join table Disk_User для хранения информации о пользователях, взявших диск
2. Role - роль пользователя. Для реализации аутентификации Spring Security
3. User - пользователь. 

Источники данных/репозитории (пакет ru.borsch.test.repositories):
1. DiscRepository - для дисков. Из примечательного - для части методов добавлен HQL через аннотации @Query, для части используется "стандартные" методы запросов, когда по имени метода типа findBy... генерирутеся соответствующий запрос (например, findByOwnerUsername - найти диск по имени пользователя владельца).
2. UserRepository - для пользователей.

Интерфейсы сервисов и их реализации (пакет ru.borsch.test.service):
1. AppUserDetailsService - сервис получения информации о пользователе из БД.
2. DiscService/DiscServiceImpl - интерфес/реализации сервиса для работы с данными дисков.
3. UserService/UserServiceImpl - интерфес/реализации сервиса для работы с данными пользователей.

Утилиты (пукет ru.borsch.test.util)
1. CustomErrorType - перекочевал из какого-то примера. Используется для отображения сообщений об ошибках

Основной класс приложения Spring Boot
1. DvdApp

# Ресурсы:

data.sql - скрипт заполнения данными БД
application.properties - файл с настройками приложений
static\css\bootstrap.css - файл стилей
static\js - папка со скриптами frontend'а
stsic\index.html - стартовая страница с тегом базового компонента frontend'а
templates\error.ftl - шаблон для отображения ошибок REST запросов 

