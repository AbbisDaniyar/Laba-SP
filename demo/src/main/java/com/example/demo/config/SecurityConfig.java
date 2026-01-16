package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.demo.jwt.JwtAuthEntryPoint;
import com.example.demo.jwt.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Конфигурационный класс для настройки безопасности Spring Security.
 * Определяет правила аутентификации, авторизации и фильтры безопасности.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    /**
     * Создает менеджер аутентификации для обработки процесса аутентификации.
     *
     * @param configuration конфигурация аутентификации
     * @return менеджер аутентификации
     * @throws Exception исключение при создании менеджера аутентификации
     */
    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Настраивает цепочку фильтров безопасности.
     * Отключает CSRF защиту, настраивает CORS, определяет правила доступа к различным URL,
     * устанавливает политику сессий как STATELESS и добавляет JWT фильтр.
     *
     * @param http объект HttpSecurity для настройки параметров безопасности
     * @return настроенная цепочка фильтров безопасности
     * @throws Exception исключение при настройке цепочки фильтров
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Отключаем CSRF защиту
            .cors(cors -> cors.configurationSource(request -> {
                // Настройка CORS для разрешения запросов с указанных источников
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080", "127.0.0.1:8080"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true);
                configuration.setMaxAge(3600L);
                return configuration;
            }))
            .authorizeHttpRequests(authorize -> {
                // Разрешаем доступ без аутентификации к Swagger документации
                authorize.requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll();

                // Разрешаем доступ без аутентификации к эндпоинтам аутентификации
                authorize.requestMatchers("/api/auth/**").permitAll();

                // Разрешаем доступ без аутентификации к основным ресурсам
                authorize.requestMatchers(
                    "/",
                    "/index.html",
                    "/app.js",
                    "/style.css",
                    "/favicon.ico"
                ).permitAll();

                // Разрешаем доступ к эндпоинту ошибок
                authorize.requestMatchers("/error").permitAll();

                // Все остальные запросы требуют аутентификации
                authorize.anyRequest().authenticated();
            })
            .sessionManagement(session -> session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)) // Устанавливаем политику сессий как STATELESS
            .exceptionHandling(exception ->
                exception.authenticationEntryPoint(jwtAuthEntryPoint)) // Обработка исключений аутентификации
            .addFilterBefore(jwtAuthFilter,
                UsernamePasswordAuthenticationFilter.class); // Добавляем JWT фильтр перед стандартным фильтром аутентификации

        return http.build();
    }

    /**
     * Создает кодировщик паролей BCrypt для шифрования паролей пользователей.
     *
     * @return кодировщик паролей
     */
    @Bean
    static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}