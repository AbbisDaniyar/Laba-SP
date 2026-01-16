package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) для запроса смены пароля.
 * Содержит текущий пароль, новый пароль и подтверждение нового пароля.
 *
 * @param currentPassword текущий пароль пользователя (не может быть пустым)
 * @param newPassword новый пароль (не может быть пустым, должен содержать минимум 8 символов)
 * @param confirmPassword подтверждение нового пароля (не может быть пустым)
 */
public record ChangePasswordRequest(
    @NotBlank(message = "Текущий пароль не может быть пустым")
    String currentPassword,

    @NotBlank(message = "Новый пароль не может быть пустым")
    @Size(min = 8, message = "Новый пароль должен содержать минимум 8 символов")
    String newPassword,

    @NotBlank(message = "Подтверждение пароля не может быть пустым")
    String confirmPassword
) {

}