# Emergency Management System

Простой Spring Boot проект для управления чрезвычайными ситуациями с Docker, PostgreSQL и Telegram ботом.

## 🚀 Быстрый старт за 5 минут

### 1. Клонирование проекта
```bash
git clone https://github.com/AbbisDaniyar/Laba-SP.git
cd Laba-SP
```

### 2. Создание конфигурационных файлов
```bash
# Основные файлы конфигурации (из примеров)
cp .env.example .env
cp docker-compose.yml.example docker-compose.yml

# Конфигурация Spring Boot приложения
cd demo
cp src/main/resources/application.yml.example src/main/resources/application.yml
cp src/main/resources/application-docker.yml.example src/main/resources/application-docker.yml
cd ..
```

### 3. Настройка переменных окружения
Отредактируйте `.env` файл:
```bash
nano .env
```

**Обязательные настройки:**
```bash
POSTGRES_PASSWORD=ваш_надежный_пароль
JWT_SECRET=$(./generate-secrets.sh)  # запустите скрипт для генерации
```

**Опциональные настройки (Telegram бот):**
```bash
TELEGRAM_BOT_TOKEN=ваш_токен_бота
TELEGRAM_CHAT_ID=ваш_chat_id
```

### 4. Генерация JWT секрета
```bash
./generate-secrets.sh
# Скрипт сгенерирует JWT_SECRET и добавит его в .env файл
```

### 5. Запуск проекта
```bash
docker-compose up --build -d
```

### 6. Проверка работоспособности
```bash
# Проверить статус контейнеров
docker-compose ps

# Просмотреть логи
docker-compose logs -f app

# Проверить health приложения
curl http://localhost:8080/actuator/health
```

## 🌐 Доступ к сервисам

- **Веб-приложение**: http://localhost:8080
- **База данных**: localhost:5432 (логин: `postgres`, пароль из `.env`)
- **Логи приложения**: `docker-compose logs -f app`
- **Логи базы данных**: `docker-compose logs -f postgres`

## 🛠 Основные команды

### Docker Compose
```bash
# Запуск
docker-compose up -d

# Пересборка и запуск
docker-compose up --build -d

# Остановка
docker-compose down

# Остановка с удалением данных
docker-compose down -v

# Просмотр логов
docker-compose logs -f app

# Перезапуск
docker-compose restart app
```

### Разработка
```bash
# Локальный запуск (без Docker)
cd demo
./mvnw spring-boot:run

# Сборка проекта
./mvnw clean package

# Тестирование
./mvnw test
```

## 📁 Структура файлов

### Файлы в репозитории (шаблоны)
```
📄 .env.example              # Пример переменных окружения
📄 docker-compose.yml.example # Пример Docker Compose конфигурации
📄 README.md                 # Эта документация
📄 generate-secrets.sh       # Скрипт генерации JWT секрета
```

### Локальные файлы (создаются пользователем)
```
📄 .env                     # Реальные переменные окружения (НЕ в git)
📄 docker-compose.yml       # Реальная Docker конфигурация (НЕ в git)
```

### Spring Boot конфигурации
```
demo/src/main/resources/
├── application.yml           # Основная конфигурация
├── application-docker.yml    # Конфигурация для Docker
└── application.yml.example   # Пример конфигурации
```

## ⚙️ Конфигурация

### База данных (PostgreSQL)
```yaml
# В docker-compose.yml
POSTGRES_DB: emergencydb
POSTGRES_USER: postgres
POSTGRES_PASSWORD: ваш_пароль  # из .env
```

### Spring Boot приложение
```yaml
# В application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/emergencydb
    username: postgres
    password: ${POSTGRES_PASSWORD}
```

### JWT аутентификация
```yaml
jwt:
  secret: ${JWT_SECRET}
  expiration: 900000        # 15 минут
  refresh-expiration: 604800000  # 7 дней
```

## 🔐 Безопасность

### Файлы, которые НЕЛЬЗЯ добавлять в git:
- `.env` - содержит пароли и секреты
- `docker-compose.yml` - может содержать пароли

### Генерация безопасных секретов
Всегда используйте скрипт для генерации JWT секрета:
```bash
./generate-secrets.sh
```

## 🐛 Устранение неполадок

### 1. Ошибка подключения к базе данных
```bash
# Проверить статус PostgreSQL
docker-compose ps postgres

# Проверить логи
docker-compose logs -f postgres

# Перезапустить БД
docker-compose restart postgres
```

### 2. Ошибка JWT аутентификации
```bash
# Проверить JWT_SECRET в .env
grep JWT_SECRET .env

# Перегенерировать секрет
./generate-secrets.sh
```

### 3. Очистка проекта
```bash
# Полная очистка
docker-compose down -v
docker system prune -a

# Пересборка
docker-compose up --build -d
```

### 4. Проверка портов
Убедитесь что порты 8080 и 5432 свободны:
```bash
sudo lsof -i :8080
sudo lsof -i :5432
```

## 📝 Миграция данных

### Резервное копирование базы данных
```bash
# Создать бекап
docker-compose exec postgres pg_dump -U postgres emergencydb > backup.sql

# Восстановить из бекапа
cat backup.sql | docker-compose exec -T postgres psql -U postgres emergencydb
```

*Проект разработан для лабораторных работ по системному программированию. Система управления чрезвычайными ситуациями в транспортной системе.*
