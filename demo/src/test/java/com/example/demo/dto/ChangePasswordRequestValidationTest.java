package com.example.demo.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Класс тестов для проверки валидации DTO запроса на изменение пароля.
 * Проверяет, что аннотации валидации работают корректно для различных сценариев.
 */
class ChangePasswordRequestValidationTest {

    private static Validator validator;

    /**
     * Инициализирует валидатор перед выполнением тестов.
     */
    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Тестирует валидацию при корректных данных.
     * Проверяет, что при валидных значениях в полях не возникает нарушений.
     */
    @Test
    void whenAllFieldsValid_thenNoViolations() {
        ChangePasswordRequest request = new ChangePasswordRequest(
                "currentPass123",
                "newPassword123",
                "newPassword123"
        );

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    /**
     * Тестирует валидацию при пустом текущем пароле.
     * Проверяет, что возникает нарушение при пустом значении текущего пароля.
     */
    @Test
    void whenCurrentPasswordBlank_thenViolation() {
        ChangePasswordRequest request = new ChangePasswordRequest(
                "",
                "newPassword123",
                "newPassword123"
        );

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Текущий пароль не может быть пустым");
    }

    /**
     * Тестирует валидацию при слишком коротком новом пароле.
     * Проверяет, что возникает нарушение при недостаточной длине нового пароля.
     */
    @Test
    void whenNewPasswordTooShort_thenViolation() {

        ChangePasswordRequest request = new ChangePasswordRequest(
                "currentPass123",
                "short",
                "short"
        );

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Новый пароль должен содержать минимум 8 символов");
    }

    /**
     * Тестирует валидацию при пустом подтверждении пароля.
     * Проверяет, что возникает нарушение при пустом значении подтверждения пароля.
     */
    @Test
    void whenConfirmPasswordBlank_thenViolation() {
        ChangePasswordRequest request = new ChangePasswordRequest(
                "currentPass123",
                "newPassword123",
                ""
        );

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Подтверждение пароля не может быть пустым");
    }


    /**
     * Тестирует валидацию при пустом новом пароле.
     * Проверяет, что возникает несколько нарушений при пустом новом пароле.
     */
    @Test
    void whenNewPasswordBlank_thenViolation() {
        ChangePasswordRequest request = new ChangePasswordRequest(
                "currentPass123",
                "",
                "newPassword123"
        );

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(2);
        List<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        assertThat(messages).contains("Новый пароль не может быть пустым");
        assertThat(messages).contains("Новый пароль должен содержать минимум 8 символов");
    }

    /**
     * Тестирует валидацию при пустых значениях во всех полях.
     * Проверяет, что возникает несколько нарушений при пустых значениях.
     */
    @Test
    void whenAllFieldsBlank_thenMultipleViolations() {
        ChangePasswordRequest request = new ChangePasswordRequest("", "", "");

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(4);
    }


}