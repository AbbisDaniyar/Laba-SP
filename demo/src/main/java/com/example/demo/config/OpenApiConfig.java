package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Конфигурационный класс для настройки OpenAPI (Swagger) документации.
 * Определяет метаданные API, информацию о безопасности и другие параметры документации.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Создает и настраивает объект OpenAPI с информацией о приложении.
     *
     * @return сконфигурированный объект OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("JWT Authentication"))
                .components(new Components()
                        .addSecuritySchemes("JWT Authentication",
                                createSecurityScheme()))
                .info(new Info()
                        .title("API Системы Экстренных Уведомлений")
                        .description("Документация REST API для системы управления автобусами и уведомлениями о событиях")
                        .version("1.0")
                        .license(new License()))
                .tags(Arrays.asList(
                        createTag("Аутентификация", "Операции, связанные с аутентификацией и управлением сессиями"),
                        createTag("Инциденты", "Операции, связанные с управлением инцидентами и уведомлениями"),
                        createTag("Автобусы", "Операции, связанные с управлением автобусами"),
                        createTag("Импорт CSV", "Операции, связанные с импортом данных из CSV-файлов"),
                        createTag("Файловая загрузка", "Операции, связанные с загрузкой файлов к инцидентам"),
                        createTag("Файловый доступ", "Операции, связанные со скачиванием файлов"),
                        createTag("PDF Отчеты", "Операции, связанные с генерацией PDF-отчетов")
                ));
    }

    /**
     * Создает тег с указанным именем и описанием.
     *
     * @param name имя тега
     * @param description описание тега
     * @return созданный тег
     */
    private Tag createTag(String name, String description) {
        return new Tag().name(name).description(description);
    }

    /**
     * Создает схему безопасности для аутентификации по JWT токенам.
     *
     * @return сконфигурированная схема безопасности
     */
    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Введите JWT токен в формате: Bearer <токен>");
    }
}