package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Класс конфигурации для настройки параметров Telegram бота.
 * Позволяет загружать настройки из application.properties/yml файла с префиксом "telegram.bot".
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramConfig {
    private String token;       // Токен для доступа к Telegram API
    private String chatId;      // ID чата для отправки сообщений
    private boolean enabled = true; // Флаг включения/выключения уведомлений через Telegram
    private String botUsername; // Имя пользователя Telegram бота
}