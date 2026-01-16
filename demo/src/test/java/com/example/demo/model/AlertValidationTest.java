package com.example.demo.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Класс тестов для проверки валидации модели оповещения.
 * Проверяет, что аннотации валидации работают корректно для различных сценариев.
 */
class AlertValidationTest {

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
        Alert alert = new Alert();
        alert.setBusId(101L);
        alert.setType(EventType.ACCIDENT);
        alert.setLocation("Москва, Ленинский проспект");
        alert.setDescription("Столкновение с другим автомобилем");

        Set<ConstraintViolation<Alert>> violations = validator.validate(alert);

        assertThat(violations).isEmpty();
    }

    /**
     * Тестирует валидацию при нулевом ID автобуса.
     * Проверяет, что возникает нарушение при отсутствии ID автобуса.
     */
    @Test
    void whenBusIdNull_thenViolation() {
        Alert alert = new Alert();
        alert.setBusId(null);
        alert.setType(EventType.ACCIDENT);
        alert.setLocation("Москва");
        alert.setDescription("Описание");

        Set<ConstraintViolation<Alert>> violations = validator.validate(alert);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Bus ID не может быть пустым");
    }

    /**
     * Тестирует валидацию при нулевом типе инцидента.
     * Проверяет, что возникает нарушение при отсутствии типа инцидента.
     */
    @Test
    void whenTypeNull_thenViolation() {
        Alert alert = new Alert();
        alert.setBusId(101L);
        alert.setType(null);
        alert.setLocation("Москва");
        alert.setDescription("Описание");

        Set<ConstraintViolation<Alert>> violations = validator.validate(alert);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Тип инцидента обязателен");
    }

    /**
     * Тестирует валидацию при пустом местоположении.
     * Проверяет, что возникает нарушение при пустом значении местоположения.
     */
    @Test
    void whenLocationBlank_thenViolation() {
        Alert alert = new Alert();
        alert.setBusId(101L);
        alert.setType(EventType.ACCIDENT);
        alert.setLocation("");
        alert.setDescription("Описание");

        Set<ConstraintViolation<Alert>> violations = validator.validate(alert);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Местоположение не может быть пустым");
    }

    /**
     * Тестирует валидацию при пустом описании.
     * Проверяет, что возникает нарушение при пустом значении описания.
     */
    @Test
    void whenDescriptionBlank_thenViolation() {
        Alert alert = new Alert();
        alert.setBusId(101L);
        alert.setType(EventType.ACCIDENT);
        alert.setLocation("Москва");
        alert.setDescription("");

        Set<ConstraintViolation<Alert>> violations = validator.validate(alert);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Описание не может быть пустым");
    }

    /**
     * Тестирует валидацию при отсутствии всех обязательных полей.
     * Проверяет, что возникает несколько нарушений при пустых значениях.
     */
    @Test
    void whenAllRequiredFieldsMissing_thenMultipleViolations() {
        Alert alert = new Alert();

        Set<ConstraintViolation<Alert>> violations = validator.validate(alert);

        assertThat(violations).hasSize(4);
    }

    /**
     * Тестирует установку значений по умолчанию при создании.
     * Проверяет, что метод onCreate устанавливает временные метки и статус по умолчанию.
     */
    @Test
    void whenPrePersist_thenDefaultValuesSet() {
        Alert alert = new Alert();
        alert.setBusId(101L);
        alert.setType(EventType.ACCIDENT);
        alert.setLocation("Москва");
        alert.setDescription("Описание");
        alert.onCreate();

        assertThat(alert.getTimestamp()).isNotNull();
        assertThat(alert.getStatus()).isEqualTo(StatusType.NEW);
    }
}