package com.example.demo.jwt;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.Token;

/**
 * Интерфейс для работы с JWT токенами.
 * Определяет методы для генерации, валидации и извлечения данных из JWT токенов.
 */
public interface JwtTokenProvider {
    /**
     * Генерирует access токен с дополнительными утверждениями.
     *
     * @param extraClaims дополнительные утверждения для токена
     * @param duration продолжительность действия токена
     * @param durationType единица измерения продолжительности
     * @param user данные пользователя для токена
     * @return сгенерированный токен
     */
    Token generateAccessToken(Map<String, Object> extraClaims, long duration, TemporalUnit durationType, UserDetails user);

    /**
     * Генерирует refresh токен.
     *
     * @param duration продолжительность действия токена
     * @param durationType единица измерения продолжительности
     * @param user данные пользователя для токена
     * @return сгенерированный токен
     */
    Token generateRefreshToken(long duration, TemporalUnit durationType, UserDetails user);

    /**
     * Проверяет валидность токена.
     *
     * @param tokenValue значение токена
     * @return true, если токен действителен, иначе false
     */
    boolean validateToken(String tokenValue);

    /**
     * Извлекает имя пользователя из токена.
     *
     * @param tokenValue значение токена
     * @return имя пользователя
     */
    String getUsernameFromToken(String tokenValue);

    /**
     * Извлекает дату истечения токена.
     *
     * @param tokenValue значение токена
     * @return дата истечения токена
     */
    LocalDateTime getExpiryDateFromToken(String tokenValue);
}