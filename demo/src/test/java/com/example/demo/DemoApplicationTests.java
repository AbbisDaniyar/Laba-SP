package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Класс тестов для проверки загрузки контекста Spring Boot приложения.
 * Проверяет, что приложение может быть успешно запущено с тестовым профилем.
 */
@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

    /**
     * Тестирует загрузку контекста Spring.
     * Проверяет, что контекст Spring Boot успешно загружается без ошибок.
     */
    @Test
    void contextLoads() {
        // Тест проверяет, что контекст Spring Boot успешно загружается
    }

    /**
     * Тестирует запуск приложения.
     * Простой тест, подтверждающий, что приложение может быть запущено.
     */
    @Test
    void testApplicationStarts() {
        assertThat(true).isTrue();
    }
}