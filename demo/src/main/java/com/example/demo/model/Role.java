package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Сущность роли пользователя.
 * Реализует интерфейс GrantedAuthority для интеграции с Spring Security.
 * Содержит уникальный ID и название роли.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор роли

    @Column(unique = true, nullable = false)
    private String name; // Название роли ("ADMIN", "MANAGER", "USER")

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}