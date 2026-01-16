package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Класс тестов для проверки реализации сервиса аутентификации.
 * Проверяет работу методов аутентификации с использованием mock-объектов.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TelegramNotificationService telegramNotificationService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private AuthServiceImpl authService;

    private User testUser;
    private Role testRole;

    /**
     * Подготавливает тестовые данные перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setId(1L);
        testRole.setName("USER");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("encodedPassword");
        testUser.setRole(testRole);
    }

    /**
     * Тестирует вход в систему с валидными учетными данными.
     * Проверяет, что метод возвращает успешный ответ при корректном логине и пароле.
     */
    @Test
    void login_WithValidCredentials_ShouldReturnSuccessResponse() {
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        Authentication authentication = mock(Authentication.class);

        when(httpServletRequest.getHeader(anyString())).thenReturn(null);
        when(httpServletRequest.getRemoteAddr()).thenReturn("192.168.1.1");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);

        com.example.demo.model.Token mockToken = new com.example.demo.model.Token();
        mockToken.setTokenValue("mock-jwt-token");
        mockToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        when(jwtTokenProvider.generateAccessToken(anyMap(), anyLong(), any(), any()))
                .thenReturn(mockToken);
        when(jwtTokenProvider.generateRefreshToken(anyLong(), any(), any()))
                .thenReturn(mockToken);

        doNothing().when(telegramNotificationService).sendAuthNotification(
                anyString(), anyString(), anyString(), anyBoolean());

        ResponseEntity<LoginResponse> response = authService.login(loginRequest, null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isLogged()).isTrue();
        assertThat(response.getHeaders().get("Set-Cookie")).isNotNull();

        verify(authenticationManager, times(1)).authenticate(any());
        verify(telegramNotificationService, times(1)).sendAuthNotification(
                anyString(), anyString(), anyString(), eq(true));
    }

    /**
     * Тестирует вход в систему с невалидными учетными данными.
     * Проверяет, что метод возвращает ошибку при некорректном логине или пароле.
     */
    @Test
    void login_WithInvalidCredentials_ShouldReturnErrorResponse() {
        LoginRequest loginRequest = new LoginRequest("wronguser", "wrongpass");

        when(httpServletRequest.getHeader(anyString())).thenReturn(null);
        when(httpServletRequest.getRemoteAddr()).thenReturn("192.168.1.1");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        doNothing().when(telegramNotificationService).sendAuthNotification(
                anyString(), anyString(), anyString(), eq(false));

        ResponseEntity<LoginResponse> response = authService.login(loginRequest, null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isLogged()).isFalse();

        verify(telegramNotificationService, times(1)).sendAuthNotification(
                eq("wronguser"), eq("UNKNOWN"), eq("192.168.1.1"), eq(false));
    }

    /**
     * Тестирует обновление токена с валидным refresh токеном.
     * Проверяет, что метод возвращает новый токен при наличии действительного refresh токена.
     */
    @Test
    void refresh_WithValidToken_ShouldReturnNewAccessToken() {
        String refreshToken = "valid-refresh-token";

        when(jwtTokenProvider.validateToken(refreshToken)).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromToken(refreshToken)).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        com.example.demo.model.Token mockToken = new com.example.demo.model.Token();
        mockToken.setTokenValue("new-access-token");
        mockToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        when(jwtTokenProvider.generateAccessToken(anyMap(), anyLong(), any(), any()))
                .thenReturn(mockToken);

        ResponseEntity<LoginResponse> response = authService.refresh(refreshToken);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isLogged()).isTrue();
        assertThat(response.getHeaders().get("Set-Cookie")).isNotNull();

        verify(jwtTokenProvider, times(1)).validateToken(refreshToken);
        verify(jwtTokenProvider, times(1)).getUsernameFromToken(refreshToken);
    }

    /**
     * Тестирует обновление токена с невалидным refresh токеном.
     * Проверяет, что метод возвращает ошибку при недействительном refresh токене.
     */
    @Test
    void refresh_WithInvalidToken_ShouldReturnError() {
        String refreshToken = "invalid-token";
        when(jwtTokenProvider.validateToken(refreshToken)).thenReturn(false);

        ResponseEntity<LoginResponse> response = authService.refresh(refreshToken);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isLogged()).isFalse();
    }

    /**
     * Тестирует получение информации о текущем пользователе.
     * Проверяет, что метод возвращает информацию о вошедшем пользователе.
     */
    @Test
    void getUserLoggedInfo_WhenAuthenticated_ShouldReturnUserInfo() {

    }

    /**
     * Тестирует получение IP-адреса клиента из заголовка X-Forwarded-For.
     * Проверяет, что метод возвращает первый IP-адрес из списка.
     */
    @Test
    void getClientIpAddress_WithXForwardedFor_ShouldReturnFirstIp() {

        when(httpServletRequest.getHeader("X-Forwarded-For"))
                .thenReturn("192.168.1.100, 192.168.1.101, 192.168.1.102");

        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException(""));


        authService.login(loginRequest, null, null);

        verify(telegramNotificationService).sendAuthNotification(
                eq("testuser"), eq("UNKNOWN"), eq("192.168.1.100"), eq(false));
    }

    /**
     * Тестирует выход из системы.
     * Проверяет, что метод очищает cookies и контекст безопасности.
     */
    @Test
    void logout_ShouldClearCookiesAndContext() {
        when(httpServletRequest.getHeader(anyString())).thenReturn(null);
        when(httpServletRequest.getRemoteAddr()).thenReturn("192.168.1.1");

        Authentication authentication = mock(Authentication.class);
        User mockUser = mock(User.class);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(mockUser.getUsername()).thenReturn("testuser");
        when(authentication.isAuthenticated()).thenReturn(true);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        doNothing().when(telegramNotificationService).sendNotification(anyString(), anyString());

        ResponseEntity<LoginResponse> response = authService.logout("access-token", "refresh-token");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isLogged()).isFalse();
        assertThat(response.getHeaders().get("Set-Cookie")).hasSize(2);

        verify(telegramNotificationService, times(1)).sendNotification(
                eq("ВЫХОД ИЗ СИСТЕМЫ"), anyString());
    }
}