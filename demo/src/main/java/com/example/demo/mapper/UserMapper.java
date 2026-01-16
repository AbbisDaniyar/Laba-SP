package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserLoggedDto;
import com.example.demo.model.User;

import java.util.Collections;

/**
 * Класс маппера для преобразования между сущностью User и DTO UserDto/UserLoggedDto.
 * Содержит статические методы для преобразования из сущности в DTO и обратно.
 */
public class UserMapper {
    /**
     * Преобразует сущность User в DTO UserDto.
     * Пароль не включается в DTO для безопасности.
     *
     * @param user сущность User для преобразования
     * @return DTO UserDto
     */
    public static UserDto userToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                null,
                user.getRole().getAuthority(),
                Collections.emptySet()
        );
    }

    /**
     * Преобразует DTO UserDto в сущность User.
     *
     * @param dto DTO UserDto для преобразования
     * @return сущность User
     */
    public static User userDtoToUser(UserDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        return user;
    }

    /**
     * Преобразует сущность User в DTO UserLoggedDto.
     * Используется для передачи информации о текущем аутентифицированном пользователе.
     *
     * @param user сущность User для преобразования
     * @return DTO UserLoggedDto
     */
    public static UserLoggedDto userToUserLoggedDto(User user) {
        return new UserLoggedDto(
                user.getUsername(),
                user.getRole().getAuthority(),
                Collections.emptySet() // Временное решение
        );
    }
}