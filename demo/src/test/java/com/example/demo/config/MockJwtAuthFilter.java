package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Фильтр для мокирования JWT аутентификации в тестовых целях.
 * Устанавливает фиктивную аутентификацию для запросов к API,
 * позволяя тестировать защищенные конечные точки без реального JWT токена.
 */
public class MockJwtAuthFilter extends OncePerRequestFilter {

    /**
     * Выполняет внутреннюю фильтрацию запроса, устанавливая мок аутентификацию
     * для запросов к API (пути, начинающиеся с "/api/").
     *
     * @param request HTTP запрос
     * @param response HTTP ответ
     * @param filterChain Цепочка фильтров
     * @throws ServletException Если возникла ошибка сервлета
     * @throws IOException Если возникла ошибка ввода-вывода
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

        // Для тестов устанавливаем мок аутентификацию
        if (request.getRequestURI().startsWith("/api/")) {
            // Проверяем заголовки для роли
            String userRole = request.getHeader("X-Test-User-Role");
            if (userRole == null) {
                userRole = "USER";
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(
                "testuser",
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole))
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}