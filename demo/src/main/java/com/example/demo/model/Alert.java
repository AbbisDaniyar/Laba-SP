package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Сущность инцидента (уведомления).
 * Представляет собой запись об инциденте, связанную с автобусом, содержащую информацию
 * о типе инцидента, времени, местоположении, описании и статусе.
 */
@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    private String filePath; // Путь к файлу, связанному с инцидентом

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор инцидента

    @NotNull(message = "Bus ID не может быть пустым")
    @Column(name = "bus_id", nullable = false)
    private Long busId; // ID автобуса, с которым связан инцидент

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", insertable = false, updatable = false)
    private Bus bus; // Связь с сущностью автобуса (игнорируется при сериализации JSON)

    @NotNull(message = "Тип инцидента обязателен")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType type; // Тип инцидента

    @Column(nullable = false)
    private LocalDateTime timestamp; // Время возникновения инцидента

    @NotBlank(message = "Местоположение не может быть пустым")
    private String location; // Местоположение инцидента

    @NotBlank(message = "Описание не может быть пустым")
    private String description; // Описание инцидента

    @Enumerated(EnumType.STRING)
    private StatusType status; // Статус инцидента

    @Column(name = "assigned_to_user_id")
    private Long assignedToUserId; // ID пользователя, которому назначен инцидент

    /**
     * Метод, вызываемый перед сохранением сущности.
     * Устанавливает текущее время, если временная метка не указана,
     * и статус NEW, если статус не указан.
     */
    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusType.NEW;
        }
    }
}