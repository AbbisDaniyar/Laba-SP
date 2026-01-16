package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность автобуса.
 * Представляет собой запись об автобусе с уникальным ID и моделью.
 */
@Entity
@Table(name = "buses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор автобуса

    @NotBlank(message = "Модель автобуса не может быть пустой")
    @Column(nullable = false)
    private String model; // Модель автобуса
}