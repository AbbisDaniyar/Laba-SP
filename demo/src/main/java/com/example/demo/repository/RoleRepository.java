package com.example.demo.repository;

import com.example.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Role (роль пользователя).
 * Предоставляет методы для поиска и управления ролями пользователей.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Находит роль по её названию.
     *
     * @param name название роли для поиска
     * @return Optional с найденной ролью или пустой Optional, если роль не найдена
     */
    Optional<Role> findByName(String name);
}