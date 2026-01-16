package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс для передачи параметров запроса на генерацию отчета.
 * Содержит информацию о периодах времени, типе отчета, фильтрах и настройках отображения.
 */
@Data
public class ReportRequest {
    private LocalDateTime startDate;    // Начальная дата для отчета
    private LocalDateTime endDate;      // Конечная дата для отчета
    private ReportType reportType;      // Тип отчета (ежедневный, еженедельный и т.д.)
    private List<Long> busIds;          // Список ID автобусов для фильтрации
    private List<String> statuses;      // Список статусов для фильтрации
    private boolean includeCharts = true;   // Включать ли диаграммы в отчет
    private boolean includeSummary = true;  // Включать ли сводную информацию в отчет

    /**
     * Перечисление типов отчетов.
     */
    public enum ReportType {
        DAILY,      // Ежедневный отчет
        WEEKLY,     // Еженедельный отчет
        MONTHLY,    // Ежемесячный отчет
        CUSTOM      // Пользовательский отчет
    }
}