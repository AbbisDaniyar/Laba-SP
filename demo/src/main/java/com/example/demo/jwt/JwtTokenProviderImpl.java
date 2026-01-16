package com.example.demo.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.enums.TokenType;
import com.example.demo.model.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

/**
 * Реализация интерфейса JwtTokenProvider для работы с JWT токенами.
 * Содержит методы для генерации, валидации и извлечения данных из JWT токенов.
 */
@Service
@RequiredArgsConstructor
public class JwtTokenProviderImpl implements JwtTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProviderImpl.class);

    @Value("${jwt.secret:mySecretKey123456789012345678901234567890}")
    private String jwtSecret; // Секретный ключ для подписи JWT токенов

    /**
     * Генерирует access токен с дополнительными утверждениями.
     *
     * @param extraClaims дополнительные утверждения для токена
     * @param duration продолжительность действия токена
     * @param durationType единица измерения продолжительности
     * @param user данные пользователя для токена
     * @return сгенерированный токен
     */
    @Override
    public Token generateAccessToken(Map<String, Object> extraClaims,
    long duration, TemporalUnit durationType, UserDetails user) {
        String username = user.getUsername();
        log.debug("Генерация access токена для пользователя: {}, продолжительность: {} {}",
                 username, duration, durationType);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plus(duration, durationType);

        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(toDate(now))
                .setExpiration(toDate(expiryDate))
                .signWith(decodeSecretKey(jwtSecret),
                SignatureAlgorithm.HS256)
                .compact();

        log.debug("Access токен сгенерирован для пользователя: {}, истекает: {}",
                 username, expiryDate);

        return new Token(TokenType.ACCESS,
        token, expiryDate, false, null);
    }

    /**
     * Генерирует refresh токен.
     *
     * @param duration продолжительность действия токена
     * @param durationType единица измерения продолжительности
     * @param user данные пользователя для токена
     * @return сгенерированный токен
     */
    @Override
    public Token generateRefreshToken(long duration,
    TemporalUnit durationType, UserDetails user) {
        String username = user.getUsername();
        log.debug("Генерация refresh токена для пользователя: {}, продолжительность: {} {}",
                 username, duration, durationType);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plus(duration, durationType);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(toDate(now))
                .setExpiration(toDate(expiryDate))
                .signWith(decodeSecretKey(jwtSecret), SignatureAlgorithm.HS256)
                .compact();

        log.debug("Refresh токен сгенерирован для пользователя: {}, истекает: {}",
                 username, expiryDate);

        return new Token(TokenType.REFRESH, token, expiryDate, false, null);
    }

    /**
     * Проверяет валидность токена.
     *
     * @param tokenValue значение токена
     * @return true, если токен действителен, иначе false
     */
    @Override
    public boolean validateToken(String tokenValue) {
        if(tokenValue == null) {
            log.debug("Валидация токена не удалась: токен пустой");
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(decodeSecretKey(jwtSecret))
                    .build()
                    .parseClaimsJws(tokenValue);

            log.debug("Валидация токена успешна");
            return true;
        } catch (JwtException e) {
            log.warn("Валидация токена не удалась: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Извлекает имя пользователя из токена.
     *
     * @param tokenValue значение токена
     * @return имя пользователя
     */
    @Override
    public String getUsernameFromToken(String tokenValue) {
        try {
            String username = extractClaim(tokenValue, Claims::getSubject);
            log.debug("Имя пользователя извлечено из токена: {}", username);
            return username;
        } catch (Exception e) {
            log.error("Ошибка извлечения имени пользователя из токена: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Извлекает дату истечения токена.
     *
     * @param tokenValue значение токена
     * @return дата истечения токена
     */
    @Override
    public LocalDateTime getExpiryDateFromToken(String tokenValue) {
        try {
            LocalDateTime expiryDate = toLocalDateTime(extractClaim(tokenValue, Claims::getExpiration));
            log.debug("Дата истечения извлечена из токена: {}", expiryDate);
            return expiryDate;
        } catch (Exception e) {
            log.error("Ошибка извлечения даты истечения из токена: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Декодирует секретный ключ из строки.
     *
     * @param secret строка секретного ключа
     * @return декодированный ключ
     */
    private Key decodeSecretKey(String secret) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(secret);
            log.trace("Секретный ключ успешно декодирован");
            return Keys.hmacShaKeyFor(decodedKey);
        } catch (Exception e) {
            log.error("Ошибка декодирования секретного ключа: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Извлекает конкретное утверждение из токена.
     *
     * @param token значение токена
     * @param claimsResolver функция для извлечения утверждения
     * @param <T> тип утверждения
     * @return извлеченное утверждение
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Извлекает все утверждения из токена.
     *
     * @param token значение токена
     * @return все утверждения токена
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodeSecretKey(jwtSecret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Ошибка извлечения claims из токена: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Преобразует LocalDateTime в Date.
     *
     * @param localDateTime локальное время
     * @return дата в формате Date
     */
    private Date toDate(LocalDateTime localDateTime) {
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        return Date.from(localDateTime.toInstant(zoneOffset));
    }

    /**
     * Преобразует Date в LocalDateTime.
     *
     * @param date дата в формате Date
     * @return локальное время
     */
    private LocalDateTime toLocalDateTime(Date date) {
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        return date.toInstant().atOffset(zoneOffset).toLocalDateTime();
    }
}