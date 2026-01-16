Простой проект Spring Boot для управления чрезвычайными ситуациями с Docker и PostgreSQL.
🚀 Быстрый старт
1. Клонирование проекта

git clone <ваш-репозиторий>
cd Laba-SP

2. Настройка конфигурации

# Копируем примеры конфигурационных файлов
cp .env.example .env
cd demo
cp src/main/resources/application.yml.example src/main/resources/application.yml
cp src/main/resources/application-docker.yml.example src/main/resources/application-docker.yml

3. Редактирование .env файла

Откройте файл .env и установите свои значения:

POSTGRES_PASSWORD=ваш_пароль_для_postgres
JWT_SECRET=сгенерируйте_32_символа_рандомных
TELEGRAM_BOT_TOKEN=ваш_токен_бота
TELEGRAM_CHAT_ID=ваш_chat_id

4. Запуск проекта

# Вернуться в корневую папку и запустить
cd ..
docker-compose up --build -d

5. Проверка работы

    Приложение: http://localhost:8080

    База данных: localhost:5432 (логин: postgres, пароль из .env)

📁 Файлы конфигурации
application.yml (основная конфигурация)

Создайте файл demo/src/main/resources/application.yml:

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/emergency_db
    username: postgres
    password: ${POSTGRES_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  secret: ${JWT_SECRET}
  expiration: 900000
  refresh-expiration: 604800000

telegram:
  bot:
    token: ${TELEGRAM_BOT_TOKEN}
    chat-id: ${TELEGRAM_CHAT_ID}

file:
  upload-dir: uploads/

application-docker.yml (для Docker)

Создайте файл demo/src/main/resources/application-docker.yml:

spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/emergency_db
    username: postgres
    password: ${POSTGRES_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update

server:
  port: 8080

logging:
  file:
    name: logs/application.log

🐳 Docker команды
Основные команды

# Сборка и запуск
docker-compose up --build -d

# Остановка
docker-compose down

# Просмотр логов
docker-compose logs -f app

# Перезапуск
docker-compose restart app

# Очистка (всех данных!)
docker-compose down -v

Проверка состояния

# Статус контейнеров
docker-compose ps

# Health check
curl http://localhost:8080/actuator/health

# Проверить базу данных
docker-compose exec postgres psql -U postgres -d emergency_db -c "\l"

💻 Локальная разработка
Требования

    Java 17

    Maven

    PostgreSQL 15

Запуск без Docker

cd demo
./mvnw spring-boot:run

Тестирование

# Запуск тестов
./mvnw test

# Сборка проекта
./mvnw clean package

🔧 Устранение проблем
1. Ошибка подключения к БД

# Проверить работает ли PostgreSQL
docker-compose logs -f postgres

# Перезапустить БД
docker-compose restart postgres

2. Ошибка JWT

Убедитесь, что в .env файле JWT_SECRET минимум 32 символа.
3. Очистка проекта

# Удалить все контейнеры и volumes
docker-compose down -v
docker system prune -a

# Пересобрать
docker-compose up --build -d

📞 Быстрые ссылки

    Веб-интерфейс: http://localhost:8080

    API документация: http://localhost:8080/swagger-ui.html

    Логи: docker-compose logs -f app
