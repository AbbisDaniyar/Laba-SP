package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Контроллер для предоставления доступа к загруженным файлам.
 * Обрабатывает запросы на скачивание файлов из директории загрузок.
 */
@Tag(name = "Файловый доступ", description = "API для скачивания загруженных файлов")
@RestController
public class FileAccessController {
    private static final Logger log = LoggerFactory.getLogger(FileAccessController.class);

    private final String uploadDir = "uploads"; // Директория для хранения загруженных файлов

    /**
     * Обрабатывает запрос на скачивание файла по имени файла.
     *
     * @param filename имя файла для скачивания
     * @return ресурс файла для скачивания или 404, если файл не найден
     */
    @Operation(summary = "Скачивание файла", description = "Обрабатывает запрос на скачивание файла по имени файла")
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@Parameter(description = "Имя файла для скачивания") @PathVariable String filename) {
        log.debug("Запрос скачивания файла: {}", filename);

        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                log.info("Файл успешно предоставлен: {}, размер: {} байт",
                        filename, resource.contentLength());
                return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            } else {
                log.warn("Файл не найден или недоступен для чтения: {}", filename);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Ошибка предоставления файла: {}, ошибка: {}", filename, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}