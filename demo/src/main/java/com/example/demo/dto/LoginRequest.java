package com.example.demo.dto;

/**
 * DTO (Data Transfer Object) для запроса аутентификации.
 * Содержит имя пользователя и пароль для входа в систему.
 *
 * @param username имя пользователя
 * @param password пароль пользователя
 */
public record LoginRequest(String username, String password){

}
