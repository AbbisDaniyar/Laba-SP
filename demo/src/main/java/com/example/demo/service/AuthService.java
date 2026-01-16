package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserLoggedDto;
import org.springframework.http.ResponseEntity;

/**
 * Сервис для аутентификации пользователей.
 * Предоставляет методы для входа, обновления токенов, выхода и получения информации о текущем пользователе.
 */
public interface AuthService {

    /**
     * Аутентифицирует пользователя и возвращает токены доступа.
     *
     * @param loginRequest Объект запроса с данными для входа (имя пользователя и пароль)
     * @param accessToken Токен доступа (если уже существует)
     * @param refreshToken Токен обновления (если уже существует)
     * @return ResponseEntity с объектом LoginResponse, содержащим токены и информацию о пользователе
     */
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    /**
     * Обновляет токены доступа с использованием токена обновления.
     *
     * @param refreshToken Токен обновления для получения новых токенов
     * @return ResponseEntity с объектом LoginResponse, содержащим новые токены
     */
    ResponseEntity<LoginResponse> refresh(String refreshToken);

    /**
     * Выполняет выход пользователя и инвалидирует токены.
     *
     * @param accessToken Токен доступа для инвалидации
     * @param refreshToken Токен обновления для инвалидации
     * @return ResponseEntity с объектом LoginResponse, подтверждающим выход
     */
    ResponseEntity<LoginResponse> logout(String accessToken, String refreshToken);

    /**
     * Получает информацию о текущем вошедшем пользователе.
     *
     * @return Объект UserLoggedDto с информацией о текущем пользователе
     */
    UserLoggedDto getUserLoggedInfo();
}