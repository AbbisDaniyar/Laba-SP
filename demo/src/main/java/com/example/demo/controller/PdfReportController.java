package com.example.demo.controller;

import com.example.demo.dto.ReportRequest;
import com.example.demo.service.PdfReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Контроллер для генерации современных PDF-отчетов.
 * Предоставляет API для создания ежедневных, еженедельных, ежемесячных и пользовательских отчетов.
 */
@Slf4j
@Tag(name = "Modern PDF Reports", description = "Современная генерация PDF отчетов")
@RestController
@RequestMapping("/api/reports/pdf-modern")
@RequiredArgsConstructor
public class PdfReportController {

    private final PdfReportService pdfReportService;

    /**
     * Генерирует ежедневный отчет в формате PDF.
     * Доступно пользователям с ролью MANAGER или ADMIN.
     *
     * @param startDate начальная дата для отчета (опционально)
     * @param endDate конечная дата для отчета (опционально)
     * @return PDF-файл с ежедневным отчетом
     */
    @Operation(summary = "Ежедневный отчет")
    @GetMapping("/daily")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<byte[]> generateDailyReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        if (startDate == null) startDate = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0);
        if (endDate == null) endDate = LocalDateTime.now().withHour(23).withMinute(59);

        byte[] pdfBytes = pdfReportService.generateDailyReport(startDate, endDate);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=daily_report_" +
                    LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    /**
     * Генерирует еженедельный отчет в формате PDF.
     * Доступно пользователям с ролью MANAGER или ADMIN.
     *
     * @param startDate начальная дата для отчета (опционально)
     * @param endDate конечная дата для отчета (опционально)
     * @return PDF-файл с еженедельным отчетом
     */
    @Operation(summary = "Еженедельный отчет")
    @GetMapping("/weekly")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<byte[]> generateWeeklyReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        if (startDate == null) startDate = LocalDateTime.now().minusWeeks(1).withHour(0).withMinute(0);
        if (endDate == null) endDate = LocalDateTime.now().withHour(23).withMinute(59);

        byte[] pdfBytes = pdfReportService.generateWeeklyReport(startDate, endDate);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=weekly_report_" +
                    LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    /**
     * Генерирует ежемесячный отчет в формате PDF.
     * Доступно пользователям с ролью MANAGER или ADMIN.
     *
     * @param startDate начальная дата для отчета (опционально)
     * @param endDate конечная дата для отчета (опционально)
     * @return PDF-файл с ежемесячным отчетом
     */
    @Operation(summary = "Ежемесячный отчет")
    @GetMapping("/monthly")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<byte[]> generateMonthlyReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        if (startDate == null) startDate = LocalDateTime.now().minusMonths(1).withHour(0).withMinute(0);
        if (endDate == null) endDate = LocalDateTime.now().withHour(23).withMinute(59);

        byte[] pdfBytes = pdfReportService.generateMonthlyReport(startDate, endDate);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=monthly_report_" +
                    LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    /**
     * Генерирует пользовательский отчет в формате PDF.
     * Доступно пользователям с ролью MANAGER или ADMIN.
     *
     * @param request параметры для пользовательского отчета
     * @return PDF-файл с пользовательским отчетом
     */
    @Operation(summary = "Пользовательский отчет")
    @PostMapping("/custom")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<byte[]> generateCustomReport(@RequestBody ReportRequest request) {
        byte[] pdfBytes = pdfReportService.generateCustomReport(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=custom_report_" +
                    LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    /**
     * Генерирует тестовый отчет в формате PDF.
     * Доступно пользователям с ролью MANAGER или ADMIN.
     *
     * @return PDF-файл с тестовым отчетом
     */
    @Operation(summary = "Тестовый отчет")
    @GetMapping("/test")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<byte[]> generateTestReport() {
        byte[] pdfBytes = pdfReportService.generateTestReport();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=test_report_" +
                    LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    /**
     * Получает статистику за указанный период.
     * Доступно пользователям с ролью USER, MANAGER или ADMIN.
     *
     * @param startDate начальная дата для получения статистики
     * @param endDate конечная дата для получения статистики
     * @return карта с данными статистики
     */
    @Operation(summary = "Статистика")
    @GetMapping("/statistics")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Map<String, Object> statistics = pdfReportService.getReportStatistics(startDate, endDate);
        return ResponseEntity.ok(statistics);
    }
}