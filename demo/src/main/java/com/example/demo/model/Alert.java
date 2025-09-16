package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    private Long id;
    private Long busId; // ID автобуса
    private EventType type; // Тип проишествия
    private LocalDateTime timestamp; //Дата и время инцидента
    private String location; // Местоположение
    private String description; // Текстовое описание деталей
    private StatusType status; // Текущий статус обработки
    private Long assignedToUserId; // Пока храним просто ID пользователя

}
