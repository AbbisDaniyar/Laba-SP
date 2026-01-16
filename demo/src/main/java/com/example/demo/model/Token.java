package com.example.demo.model;

import com.example.demo.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс для представления токена аутентификации.
 * Содержит информацию о типе токена, значении, дате истечения, статусе отзыва и ID пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private TokenType type;        // Тип токена (ACCESS или REFRESH)
    private String tokenValue;     // Значение токена
    private LocalDateTime expiryDate; // Дата истечения токена
    private boolean revoked;       // Признак отзыва токена
    private Long userId;           // ID пользователя, которому принадлежит токен
}