package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Реализация сервиса загрузки деталей пользователя.
 * Предоставляет метод для загрузки информации о пользователе по его имени,
 * с использованием кэширования для повышения производительности.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    /**
     * Загружает детали пользователя по его имени.
     * Результат кэшируется с использованием Spring Cache.
     *
     * @param username Имя пользователя для загрузки
     * @return Объект UserDetails с информацией о пользователе
     * @throws UsernameNotFoundException Если пользователь с указанным именем не найден
     */
    @Cacheable(value = "userDetails", key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Загрузка деталей пользователя по имени: {}", username);

        UserDetails userDetails = userRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.warn("Пользователь не найден: {}", username);
                    return new UsernameNotFoundException("Пользователь не найден: " + username);
                }
        );

        log.debug("Детали пользователя успешно загружены: {}", username);

        return userDetails;
    }
}