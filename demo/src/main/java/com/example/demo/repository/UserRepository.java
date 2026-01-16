package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями User.
 * Предоставляет методы для доступа к данным пользователей в базе данных.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по имени пользователя с загрузкой связанной роли.
     *
     * @param username Имя пользователя для поиска
     * @return Объект Optional, содержащий найденного пользователя с ролью, или пустой Optional, если пользователь не найден
     */
    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    /**
     * Проверяет существование пользователя по имени пользователя.
     *
     * @param username Имя пользователя для проверки
     * @return true если пользователь существует, иначе false
     */
    boolean existsByUsername(String username);
}