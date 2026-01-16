package com.example.demo.jwt;

import com.example.demo.enums.TokenType;
import com.example.demo.model.Token;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестов для проверки реализации провайдера JWT токенов.
 * Проверяет генерацию, валидацию и извлечение данных из JWT токенов.
 */
@ExtendWith(MockitoExtension.class)
class JwtTokenProviderImplTest {

    @InjectMocks
    private JwtTokenProviderImpl jwtTokenProvider;

    private UserDetails userDetails;

    private final String secretKey = "bXlTZWNyZXRLZXkxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkw";

    /**
     * Подготавливает тестовые данные перед каждым тестом.
     * Устанавливает секретный ключ и создает тестового пользователя.
     */
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", secretKey);

        userDetails = User.withUsername("testuser")
                .password("password")
                .roles("USER")
                .build();
    }

    /**
     * Тестирует генерацию токена доступа.
     * Проверяет, что сгенерированный токен содержит корректные данные и валиден.
     */
    @Test
    void generateAccessToken_ShouldGenerateValidToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");
        claims.put("username", "testuser");

        Token token = jwtTokenProvider.generateAccessToken(
                claims, 15, ChronoUnit.MINUTES, userDetails);

        assertThat(token).isNotNull();
        assertThat(token.getType()).isEqualTo(TokenType.ACCESS);
        assertThat(token.getTokenValue()).isNotBlank();
        assertThat(token.getExpiryDate()).isAfter(LocalDateTime.now());

        assertTrue(jwtTokenProvider.validateToken(token.getTokenValue()));
        assertEquals("testuser", jwtTokenProvider.getUsernameFromToken(token.getTokenValue()));
    }

    /**
     * Тестирует генерацию токена обновления.
     * Проверяет, что сгенерированный токен содержит корректные данные и валиден.
     */
    @Test
    void generateRefreshToken_ShouldGenerateValidToken() {
        Token token = jwtTokenProvider.generateRefreshToken(
                7, ChronoUnit.DAYS, userDetails);

        assertThat(token).isNotNull();
        assertThat(token.getType()).isEqualTo(TokenType.REFRESH);
        assertThat(token.getTokenValue()).isNotBlank();
        assertThat(token.getExpiryDate()).isAfter(LocalDateTime.now());

        assertEquals("testuser", jwtTokenProvider.getUsernameFromToken(token.getTokenValue()));
    }

    /**
     * Тестирует валидацию действительного токена.
     * Проверяет, что метод валидации возвращает true для действительного токена.
     */
    @Test
    void validateToken_WithValidToken_ShouldReturnTrue() {
        Token token = jwtTokenProvider.generateAccessToken(
                new HashMap<>(), 15, ChronoUnit.MINUTES, userDetails);

        boolean isValid = jwtTokenProvider.validateToken(token.getTokenValue());

        assertTrue(isValid);
    }

    /**
     * Тестирует валидацию недействительного токена.
     * Проверяет, что метод валидации возвращает false для недействительного или null токена.
     */
    @Test
    void validateToken_WithInvalidToken_ShouldReturnFalse() {
        assertFalse(jwtTokenProvider.validateToken("invalid.token.here"));
        assertFalse(jwtTokenProvider.validateToken(null));
    }

    /**
     * Тестирует извлечение имени пользователя из токена.
     * Проверяет, что метод возвращает корректное имя пользователя из действительного токена.
     */
    @Test
    void getUsernameFromToken_ShouldReturnUsername() {
        Token token = jwtTokenProvider.generateAccessToken(
                new HashMap<>(), 15, ChronoUnit.MINUTES, userDetails);

        String username = jwtTokenProvider.getUsernameFromToken(token.getTokenValue());

        assertEquals("testuser", username);
    }

    /**
     * Тестирует извлечение имени пользователя из недействительного токена.
     * Проверяет, что метод возвращает null для недействительного токена.
     */
    @Test
    void getUsernameFromToken_WithInvalidToken_ShouldReturnNull() {
        assertNull(jwtTokenProvider.getUsernameFromToken("invalid.token"));
    }

    /**
     * Тестирует извлечение даты истечения токена.
     * Проверяет, что метод возвращает корректную дату истечения для действительного токена.
     */
    @Test
    void getExpiryDateFromToken_ShouldReturnExpiryDate() {
        Token token = jwtTokenProvider.generateAccessToken(
                new HashMap<>(), 15, ChronoUnit.MINUTES, userDetails);

        LocalDateTime expiryDate = jwtTokenProvider.getExpiryDateFromToken(token.getTokenValue());

        assertNotNull(expiryDate);
        assertTrue(expiryDate.isAfter(LocalDateTime.now()));
    }

    /**
     * Тестирует добавление пользовательских утверждений в токен.
     * Проверяет, что токен может содержать дополнительные пользовательские утверждения.
     */
    @Test
    void tokens_ShouldContainCustomClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_ADMIN");
        claims.put("custom", "value");

        Token token = jwtTokenProvider.generateAccessToken(
                claims, 15, ChronoUnit.MINUTES, userDetails);

        assertThat(token).isNotNull();
        assertThat(token.getType()).isEqualTo(TokenType.ACCESS);
        assertThat(token.getTokenValue()).isNotBlank();

        assertTrue(jwtTokenProvider.validateToken(token.getTokenValue()));

        String username = jwtTokenProvider.getUsernameFromToken(token.getTokenValue());
        assertEquals("testuser", username);
    }
}