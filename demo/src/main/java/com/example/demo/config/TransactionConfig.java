package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Конфигурационный класс для управления транзакциями в приложении.
 * Включает поддержку аннотаций @Transactional для управления транзакциями.
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    // Управление транзакциями включается автоматически в Spring Boot
    // Этот класс конфигурации явно включает его для ясности
}