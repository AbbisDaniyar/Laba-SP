package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурационный класс для настройки веб-слоя приложения.
 * Реализует интерфейс WebMvcConfigurer для настройки контроллеров представлений.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Добавляет контроллеры представлений для маршрутизации SPA (Single Page Application).
     * Перенаправляет все указанные пути на index.html для корректной работы клиентского роутинга.
     *
     * @param registry реестр контроллеров представлений
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");      // Главная страница
        registry.addViewController("/alerts").setViewName("forward:/index.html"); // Страница уведомлений
        registry.addViewController("/alerts/**").setViewName("forward:/index.html"); // Подстраницы уведомлений
    }
}