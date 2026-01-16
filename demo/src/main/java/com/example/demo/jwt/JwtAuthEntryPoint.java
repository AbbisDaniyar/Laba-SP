package com.example.demo.jwt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Компонент для обработки случаев, когда аутентификация пользователя не удалась.
 * Реализует интерфейс AuthenticationEntryPoint и возвращает 401 Unauthorized
 * с подробной информацией об ошибке в формате JSON.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    /**
     * Метод вызывается, когда пользователь пытается получить доступ к защищенному ресурсу
     * без правильной аутентификации. Возвращает 401 ответ с деталями ошибки.
     *
     * @param request HTTP запрос
     * @param response HTTP ответ
     * @param authException исключение аутентификации
     * @throws IOException в случае ошибки ввода-вывода
     * @throws ServletException в случае ошибки сервлета
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("Требуется аутентификация для пути: {}, ошибка: {}",
                request.getServletPath(), authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        final Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        body.put("message", authException.getMessage());
        body.put("details", request.getServletPath());

        log.debug("Возвращается ответ 401: {}", body);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}