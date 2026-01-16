package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для представления результата импорта данных из CSV-файла.
 * Содержит информацию о количестве успешно обработанных записей,
 * количестве неудачных попыток, список ошибок и ID созданных инцидентов.
 */
@Data
@NoArgsConstructor
public class CsvImportResult {
    private int successCount;          // Количество успешно импортированных записей
    private int failedCount;           // Количество неудачных попыток импорта
    private List<String> errors = new ArrayList<>();      // Список ошибок при импорте
    private List<Long> createdAlertIds = new ArrayList<>(); // Список ID созданных инцидентов

    /**
     * Конструктор для создания результата импорта с заданными параметрами.
     *
     * @param successCount количество успешно импортированных записей
     * @param failedCount количество неудачных попыток импорта
     * @param errors список ошибок при импорте
     * @param createdAlertIds список ID созданных инцидентов
     */
    public CsvImportResult(int successCount, int failedCount, List<String> errors, List<Long> createdAlertIds) {
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.errors = errors != null ? errors : new ArrayList<>();
        this.createdAlertIds =createdAlertIds != null ? createdAlertIds : new ArrayList<>();
    }

    /**
     * Проверяет, были ли ошибки при импорте.
     *
     * @return true, если были ошибки, иначе false
     */
    public boolean hasError() {
        return !errors.isEmpty();
    }
}