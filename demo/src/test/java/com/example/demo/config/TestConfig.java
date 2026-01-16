package com.example.demo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурация для тестов.
 * Предоставляет бины, необходимые для выполнения тестов.
 */
@TestConfiguration
public class TestConfig {

    /**
     * Создает и возвращает энкодер паролей для использования в тестах.
     *
     * @return BCryptPasswordEncoder для хеширования паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}