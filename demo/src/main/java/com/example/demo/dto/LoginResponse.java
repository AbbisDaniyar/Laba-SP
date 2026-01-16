package com.example.demo.dto;

/**
 * DTO (Data Transfer Object) для ответа на запрос аутентификации.
 * Содержит информацию о статусе аутентификации и роли пользователя.
 *
 * @param isLogged статус аутентификации (успешно или нет)
 * @param roles роли пользователя в системе
 */
public record LoginResponse(boolean isLogged, String roles) {

}