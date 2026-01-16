package com.example.demo.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Перечисление прав доступа пользователей в системе.
 * Реализует интерфейс GrantedAuthority для интеграции с Spring Security.
 */
public enum Permission implements GrantedAuthority {
    ALERTS_READ,    // Право на чтение инцидентов
    ALERTS_WRITE,   // Право на создание и обновление инцидентов
    ALERTS_DELETE;  // Право на удаление инцидентов

    @Override
    public String getAuthority() {
        return name();
    }
}