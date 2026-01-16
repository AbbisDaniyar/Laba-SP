package com.example.demo.service;

import com.example.demo.dto.UserDto;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления пользователями.
 * Предоставляет методы для выполнения операций CRUD над пользователями.
 */
public interface UserService {

    /**
     * Получает список всех пользователей.
     *
     * @return Список объектов UserDto, представляющих всех пользователей
     */
    List<UserDto> getAllUsers();

    /**
     * Получает пользователя по его ID.
     *
     * @param id Уникальный идентификатор пользователя
     * @return Объект Optional, содержащий UserDto если пользователь найден, иначе пустой Optional
     */
    Optional<UserDto> getUserById(Long id);

    /**
     * Получает пользователя по имени пользователя.
     *
     * @param username Имя пользователя
     * @return Объект UserDto, представляющий пользователя с указанным именем
     */
    UserDto getUserByUsername(String username);

    /**
     * Создает нового пользователя.
     *
     * @param userDto Объект UserDto с данными нового пользователя
     * @return Объект UserDto, представляющий созданного пользователя
     */
    UserDto createUser(UserDto userDto);

    /**
     * Обновляет существующего пользователя.
     *
     * @param id Уникальный идентификатор пользователя для обновления
     * @param userDto Объект UserDto с новыми данными пользователя
     * @return Объект UserDto, представляющий обновленного пользователя
     */
    UserDto updateUser(Long id, UserDto userDto);

    /**
     * Удаляет пользователя по его ID.
     *
     * @param id Уникальный идентификатор пользователя для удаления
     */
    void deleteUser(Long id);

    /**
     * Проверяет существование пользователя по имени пользователя.
     *
     * @param username Имя пользователя для проверки
     * @return true если пользователь существует, иначе false
     */
    boolean userExists(String username);

}