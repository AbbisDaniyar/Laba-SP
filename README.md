# Лабораторная работа по системному программированию

Этот репозиторий содержит проект, разработанный в рамках лабораторной работы по системному программированию. Проект представляет собой Spring Boot приложение, реализующее систему управления автобусами и уведомлениями о событиях.

## Структура проекта

```
Laba SP/
├── .env
├── .env.example
├── .gitignore
├── backup.sql''
├── demo/
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── -p/
│   ├── .mvn/
│   │   └── wrapper/
│   │       └── maven-wrapper.properties
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/demo/
│   │   │   │       ├── DemoApplication.java
│   │   │   │       ├── config/
│   │   │   │       │   ├── CacheConfig.java
│   │   │   │       │   ├── SecurityConfig.java
│   │   │   │       │   ├── TelegramConfig.java
│   │   │   │       │   ├── TransactionConfig.java
│   │   │   │       │   └── WebConfig.java
│   │   │   │       ├── controller/
│   │   │   │       │   ├── AlertController.java
│   │   │   │       │   ├── AuthenticationController.java
│   │   │   │       │   ├── BusController.java
│   │   │   │       │   ├── CsvImportController.java
│   │   │   │       │   ├── FileAccessController.java
│   │   │   │       │   ├── FileController.java
│   │   │   │       │   └── PdfReportController.java
│   │   │   │       ├── dto/
│   │   │   │       │   ├── BusDto.java
│   │   │   │       │   ├── ChangePasswordRequest.java
│   │   │   │       │   ├── CsvImportResult.java
│   │   │   │       │   ├── LoginRequest.java
│   │   │   │       │   ├── LoginResponse.java
│   │   │   │       │   ├── ReportRequest.java
│   │   │   │       │   ├── UserDto.java
│   │   │   │       │   └── UserLoggedDto.java
│   │   │   │       ├── enums/
│   │   │   │       │   ├── Permission.java
│   │   │   │       │   └── TokenType.java
│   │   │   │       ├── exception/
│   │   │   │       │   ├── AlertNotFoundException.java
│   │   │   │       │   ├── BusNotFoundException.java
│   │   │   │       │   └── GlobalExceptionHandler.java
│   │   │   │       ├── jwt/
│   │   │   │       │   ├── JwtAuthEntryPoint.java
│   │   │   │       │   ├── JwtAuthFilter.java
│   │   │   │       │   ├── JwtTokenProvider.java
│   │   │   │       │   └── JwtTokenProviderImpl.java
│   │   │   │       ├── mapper/
│   │   │   │       │   ├── BusMapper.java
│   │   │   │       │   └── UserMapper.java
│   │   │   │       ├── model/
│   │   │   │       │   ├── Alert.java
│   │   │   │       │   ├── Bus.java
│   │   │   │       │   ├── EventType.java
│   │   │   │       │   ├── Role.java
│   │   │   │       │   ├── StatusType.java
│   │   │   │       │   ├── Token.java
│   │   │   │       │   └── User.java
│   │   │   │       ├── repository/
│   │   │   │       │   ├── AlertRepository.java
│   │   │   │       │   ├── BusRepository.java
│   │   │   │       │   ├── RoleRepository.java
│   │   │   │       │   └── UserRepository.java
│   │   │   │       ├── service/
│   │   │   │       │   ├── AlertService.java
│   │   │   │       │   ├── AuthService.java
│   │   │   │       │   ├── AuthServiceImpl.java
│   │   │   │       │   ├── BusService.java
│   │   │   │       │   ├── CachedAlertService.java
│   │   │   │       │   ├── CsvImportService.java
│   │   │   │       │   ├── FileService.java
│   │   │   │       │   ├── PdfReportService.java
│   │   │   │       │   ├── TelegramNotificationService.java
│   │   │   │       │   ├── UserDetailsServiceImpl.java
│   │   │   │       │   ├── UserService.java
│   │   │   │       │   └── UserServiceImpl.java
│   │   │   │       └── specification/
│   │   │   │           └── AlertSpecification.java
│   │   │   └── resources/
│   │   │       ├── application-docker.yml
│   │   │       ├── application-docker.yml.example
│   │   │       ├── application.yml
│   │   │       ├── application.yml.example
│   │   │       ├── fonts/
│   │   │       │   └── arial.ttf
│   │   │       └── static/
│   │   │           ├── favicon.ico
│   │   │           └── index.html
│   │   └── test/
│   │       └── java/
│   │           └── com/example/demo/
│   │               ├── DemoApplicationTests.java
│   │               ├── config/
│   │               │   ├── MockJwtAuthFilter.java
│   │               │   ├── SecurityConfigTest.java
│   │               │   ├── TestConfig.java
│   │               │   └── TestSecurityConfig.java
│   │               ├── controller/
│   │               │   ├── AlertControllerIntegrationTest.java
│   │               │   ├── AuthenticationControllerIntegrationTest.java
│   │               │   └── BusControllerIntegrationTest.java
│   │               ├── dto/
│   │               │   └── ChangePasswordRequestValidationTest.java
│   │               ├── jwt/
│   │               │   └── JwtTokenProviderImplTest.java
│   │               ├── model/
│   │               │   └── AlertValidationTest.java
│   │               └── service/
│   │                   ├── AlertServiceTest.java
│   │                   ├── AuthServiceImplTest.java
│   │                   └── UserServiceImplTest.java
│   └── target/
├── docker-compose.yml
├── docker-compose.yml.example
├── README.md (этот файл)
├── spring-petclinic/ (пустой каталог)
├── test_alerts.csv
├── uploads/ (каталог для загрузки файлов)
└── .vscode/
    ├── launch.json
    └── settings.json
```

## Описание компонентов

### demo
Это основное Spring Boot приложение. Оно включает в себя:
- REST API для управления автобусами и уведомлениями.
- Аутентификацию и авторизацию с использованием JWT.
- Интеграцию с Telegram для отправки уведомлений.
- Возможность импорта данных из CSV.
- Генерацию PDF-отчетов.
- Кэширование данных.
- Обработку исключений.
- Конфигурацию безопасности.
- Конфигурацию транзакций.
- Конфигурацию веб-слоя.
- Модели данных (Alert, Bus, User, Role, Token).
- Репозитории для работы с базой данных.
- Сервисы для бизнес-логики.
- Контроллеры для обработки HTTP-запросов.
- DTO (Data Transfer Objects) для передачи данных между слоями.
- Enums для определения разрешений и типов токенов.
- Фильтры JWT для аутентификации.
- Мапперы для преобразования объектов.
- Спецификации для построения сложных запросов к базе данных.
- Тесты (юнит-тесты и интеграционные тесты).

### .env и .env.example
Файлы для хранения переменных окружения. `.env.example` содержит примеры переменных, которые нужно задать в `.env`.

### docker-compose.yml и docker-compose.yml.example
Файлы для запуска приложения и зависимостей (база данных, Redis, MinIO) с помощью Docker Compose. `docker-compose.yml.example` содержит примеры конфигурации.

### backup.sql''
Файл дампа базы данных.

### test_alerts.csv
CSV-файл с тестовыми данными для уведомлений.

### uploads
Каталог для загрузки файлов пользователями.

### spring-petclinic
Пустой каталог, вероятно, зарезервирован для будущего использования.

## Установка и запуск

1.  Убедитесь, что у вас установлены Java 17+, Maven 3.6+ и Docker.
2.  Склонируйте репозиторий.
3.  Скопируйте `.env.example` в `.env` и настройте переменные окружения.
4.  Скопируйте `docker-compose.yml.example` в `docker-compose.yml` и настройте конфигурацию.
5.  Запустите зависимости (база данных, Redis, MinIO) с помощью `docker-compose up -d`.
6.  Запустите приложение с помощью `mvn spring-boot:run` или соберите JAR-файл с помощью `mvn clean package` и запустите его с помощью `java -jar target/demo-0.0.1-SNAPSHOT.jar`.

## Использование

Приложение предоставляет REST API для управления автобусами и уведомлениями. Также доступен веб-интерфейс по адресу `http://localhost:8080`.

## Технологии

-   Java 17+
-   Spring Boot 3+
-   Spring Data JPA
-   Spring Security
-   JWT
-   Hibernate
-   PostgreSQL (или другая реляционная БД)
-   Redis
-   MinIO
-   Docker
-   Maven
-   Lombok
-   MapStruct
-   Thymeleaf (для генерации PDF)
-   Telegram Bot API

## Функционал

Приложение предоставляет следующие возможности:

*   **Управление автобусами:**
    *   Создание, чтение, обновление и удаление информации об автобусах.
    *   Поиск автобусов по модели.
    *   Управление статусом автобуса (например, "в эксплуатации", "в ремонте").
    *   Управление информацией о водителе, дате последнего и следующего технического обслуживания.

*   **Управление уведомлениями (инцидентами):**
    *   Создание, чтение, обновление и удаление уведомлений.
    *   Назначение уведомлений автобусам и пользователям.
    *   Управление статусом уведомления (например, "новое", "в работе", "решено").
    *   Фильтрация уведомлений по статусу, автобусу или пользователю.
    *   Импорт уведомлений из CSV-файлов.
    *   Кэширование уведомлений для повышения производительности.

*   **Аутентификация и авторизация:**
    *   Регистрация и вход пользователей.
    *   Аутентификация с использованием JWT-токенов (access и refresh).
    *   Разграничение прав доступа на основе ролей (USER, MANAGER, ADMIN).
    *   Возможность смены пароля.

*   **Интеграция с Telegram:**
    *   Отправка уведомлений в Telegram-чат.

*   **Файловая система:**
    *   Загрузка файлов на сервер.
    *   Скачивание загруженных файлов.

*   **Генерация отчетов:**
    *   Генерация PDF-отчетов различных типов (ежедневные, еженедельные, ежемесячные, пользовательские).
    *   Получение статистики по уведомлениям за указанный период.

## Основные запросы (REST API)

### Аутентификация (`/api/auth`)

*   `POST /api/auth/login` - Вход пользователя. Принимает `username` и `password`. Возвращает JWT-токены.
*   `POST /api/auth/refresh` - Обновление access-токена по refresh-токену.
*   `POST /api/auth/logout` - Выход из системы. Инвалидирует токены.
*   `GET /api/auth/info` - Получение информации о текущем пользователе.
*   `PUT /api/auth/change_password` - Смена пароля. Требует `currentPassword`, `newPassword`, `confirmPassword`.

### Управление автобусами (`/api/buses`)

*   `GET /api/buses` - Получить список всех автобусов.
*   `GET /api/buses/{id}` - Получить автобус по ID.
*   `GET /api/buses/search?model={model}` - Поиск автобусов по модели.
*   `POST /api/buses` - Создать новый автобус. Требует JSON с данными автобуса.
*   `PUT /api/buses/{id}` - Обновить информацию об автобусе. Требует JSON с новыми данными.
*   `DELETE /api/buses/{id}` - Удалить автобус по ID.

### Управление уведомлениями (`/api/alerts`)

*   `GET /api/alerts` - Получить список всех уведомлений. Можно фильтровать по статусу (`?status=PENDING`).
*   `GET /api/alerts/{id}` - Получить уведомление по ID.
*   `GET /api/alerts/bus/{busId}` - Получить уведомления по ID автобуса.
*   `GET /api/alerts/user/{userId}` - Получить уведомления, назначенные пользователю.
*   `POST /api/alerts` - Создать новое уведомление. Требует JSON с данными уведомления.
*   `PUT /api/alerts/{id}/status?status={status}` - Обновить статус уведомления.
*   `PUT /api/alerts/{id}/assign?userId={userId}` - Назначить уведомление пользователю.
*   `DELETE /api/alerts/{id}` - Удалить уведомление по ID.
*   `POST /api/alerts/import-csv` - Импорт уведомлений из CSV-файла (multipart form-data).
*   `GET /api/alerts/cache-test` - Тестирование производительности кэширования (доступно только администратору).
*   `POST /api/alerts/cache/clear` - Очистка кэша уведомлений (доступно только администратору).

### Файлы (`/api/files` и `/files`)

*   `POST /api/files/upload` - Загрузка файла (multipart form-data).
*   `GET /files/{filename}` - Скачивание файла по имени.

### Отчеты (`/api/reports/pdf-modern`)

*   `GET /api/reports/pdf-modern/daily?startDate={date}&endDate={date}` - Генерация ежедневного отчета.
*   `GET /api/reports/pdf-modern/weekly?startDate={date}&endDate={date}` - Генерация еженедельного отчета.
*   `GET /api/reports/pdf-modern/monthly?startDate={date}&endDate={date}` - Генерация ежемесячного отчета.
*   `POST /api/reports/pdf-modern/custom` - Генерация пользовательского отчета. Требует JSON с параметрами отчета.
*   `GET /api/reports/pdf-modern/test` - Генерация тестового отчета.
*   `GET /api/reports/pdf-modern/statistics?startDate={date}&endDate={date}` - Получение статистики.

## Инструкции по использованию

1.  **Запуск приложения:** Следуйте инструкциям в разделе "Установка и запуск" выше.
2.  **Аутентификация:** Для доступа к большинству функций приложения необходимо сначала аутентифицироваться. Используйте `POST /api/auth/login` для получения JWT-токенов. Токены будут установлены в cookies.
3.  **Использование API:** После аутентификации вы можете использовать остальные эндпоинты API. Убедитесь, что у вас есть необходимые права (роль) для выполнения конкретных действий.
4.  **Веб-интерфейс:** Приложение также предоставляет простой веб-интерфейс для загрузки файлов по адресу `http://localhost:8080`.
5.  **Swagger UI:** Если Swagger включен (обычно в профиле разработки), вы можете изучить API через Swagger UI по адресу `http://localhost:8080/swagger-ui/index.html`.

# Docker и тестирование

## Сборка и запуск с помощью Docker

### Сборка Docker-образа

Для сборки Docker-образа приложения выполните следующую команду в директории demo:

docker build -t laba-sp-app .

### Запуск приложения в Docker

Для запуска приложения в контейнере Docker:

docker run -p 8080:8080 --env-file .env laba-sp-app


### Запуск с помощью Docker Compose

Для запуска всего приложения со всеми зависимостями (база данных, MinIO) с помощью Docker Compose:

1. Скопируйте docker-compose.yml.example в docker-compose.yml и настройте конфигурацию:
   cp docker-compose.yml.example docker-compose.yml

2. Запустите все сервисы:
   docker-compose up -d

3. Для остановки сервисов:
   docker-compose down

## Тестирование

### Запуск тестов

Для запуска всех тестов используйте Maven:

cd demo
mvn test

Для запуска только юнит-тестов:
mvn test -Dtest=Test

Для запуска только интеграционных тестов:
mvn test -Dtest=IntegrationTest

Для запуска тестов с отчетом о покрытии кода (если настроено):
mvn verify

Для запуска тестов без сборки проекта:
mvn test -DskipTests=false

### Типы тестов

Проект включает в себя различные типы тестов:

- Юнит-тесты: Проверяют отдельные компоненты (сервисы, контроллеры, мапперы).
- Интеграционные тесты: Проверяют взаимодействие между компонентами и работу с базой данных.
- Тесты безопасности: Проверяют корректность аутентификации и авторизации.
- Тесты валидации: Проверяют корректность валидации входных данных.

Примеры тестов находятся в директории demo/src/test/java/com/example/demo/:

- AlertServiceTest.java - юнит-тесты для сервиса уведомлений
- AlertControllerIntegrationTest.java - интеграционные тесты для контроллера уведомлений
- AuthServiceImplTest.java - юнит-тесты для сервиса аутентификации
- JwtTokenProviderImplTest.java - тесты для провайдера JWT-токенов
- ChangePasswordRequestValidationTest.java - тесты валидации запроса смены пароля

### Запуск тестов в Docker

Для запуска тестов внутри Docker-контейнера:

# Сборка с выполнением тестов
docker build -t laba-sp-app-test .

# Или запуск тестов в уже собранном образе
docker run --rm -v .:/app -w /app maven:3.9-eclipse-temurin-17 mvn test
