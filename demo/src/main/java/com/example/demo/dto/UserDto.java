package com.example.demo.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO (Data Transfer Object) для передачи данных о пользователе.
 * Содержит информацию о пользователе, включая его ID, имя, пароль, роль и права доступа.
 * Реализует интерфейс Serializable для возможности сериализации.
 *
 * @param id уникальный идентификатор пользователя
 * @param username имя пользователя
 * @param password пароль пользователя
 * @param role роль пользователя в системе
 * @param permissions набор прав доступа пользователя
 */
public record UserDto(
        Long id,
        String username,
        String password,
        String role,
        Set<String> permissions
) implements Serializable { }