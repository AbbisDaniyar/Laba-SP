package com.example.demo.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Конфигурационный класс для настройки кэширования в приложении.
 * Включает кэширование и определяет имена кэшей, используемых в приложении.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Создает и настраивает менеджер кэша для приложения.
     * Определяет имена кэшей, которые будут использоваться для хранения различных типов данных.
     *
     * @return настроенный менеджер кэша
     */
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setCacheNames(List.of(
            "alerts",           // Кэш для уведомлений
            "alertsByStatus",   // Кэш для уведомлений по статусу
            "alertsByBus",      // Кэш для уведомлений по автобусу
            "alertsByUser",     // Кэш для уведомлений по пользователю
            "userDetails",      // Кэш для деталей пользователя
            "buses",            // Кэш для списка автобусов
            "bus"               // Кэш для одного автобуса
        ));
        cacheManager.setAllowNullValues(false); // Не разрешать null значения в кэше
        return cacheManager;
    }
}