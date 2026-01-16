package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) для передачи данных об автобусе.
 * Используется для передачи информации об автобусе между слоями приложения.
 *
 * @param id ID автобуса
 * @param model модель автобуса (не может быть пустой)
 */
public record BusDto(
    Long id,

    @NotBlank(message = "Модель автобуса не может быть пустой")
    String model
) {}