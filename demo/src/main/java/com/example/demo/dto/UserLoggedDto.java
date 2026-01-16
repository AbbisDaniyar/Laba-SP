package com.example.demo.dto;

import java.util.Set;

/**
 * DTO (Data Transfer Object) для передачи информации о текущем аутентифицированном пользователе.
 * Содержит имя пользователя, его роль и права доступа.
 *
 * @param username имя пользователя
 * @param role роль пользователя в системе
 * @param permissions набор прав доступа пользователя
 */
public record UserLoggedDto(String username, String role, Set<String> permissions) {

}