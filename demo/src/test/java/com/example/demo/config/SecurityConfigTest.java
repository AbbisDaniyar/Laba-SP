package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Класс тестов для проверки конфигурации безопасности.
 * Тестирует доступность публичных конечных точек и защиту защищенных ресурсов.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Тестирует доступность публичных конечных точек без аутентификации.
     * Проверяет, что Swagger UI, API документация и главная страница доступны без авторизации.
     *
     * @throws Exception Если возникла ошибка при выполнении теста
     */
    @Test
    void publicEndpoints_ShouldBeAccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    /**
     * Тестирует, что защищенная конечная точка требует аутентификации.
     * Проверяет, что запрос к /api/alerts без аутентификации возвращает статус 401 Unauthorized.
     *
     * @throws Exception Если возникла ошибка при выполнении теста
     */
    @Test
    void alertsEndpoint_WithoutAuthentication_ShouldBeUnauthorized() throws Exception {
        mockMvc.perform(get("/api/alerts"))
                .andExpect(status().isUnauthorized());
    }
}