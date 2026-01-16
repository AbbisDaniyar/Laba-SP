package com.example.demo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация безопасности для тестов.
 * Определяет цепочку фильтров безопасности, используемую в тестовых сценариях.
 */
@TestConfiguration
public class TestSecurityConfig {

    /**
     * Создает и настраивает цепочку фильтров безопасности для тестов.
     * Отключает CSRF, разрешает доступ к конечным точкам аутентификации и Swagger,
     * требует аутентификацию для остальных запросов.
     *
     * @param http Объект HttpSecurity для настройки безопасности
     * @return Настроенный SecurityFilterChain
     * @throws Exception Если возникла ошибка при настройке
     */
    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new MockJwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}