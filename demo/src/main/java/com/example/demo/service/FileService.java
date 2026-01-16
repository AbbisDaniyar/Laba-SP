package com.example.demo.service;

import com.example.demo.service.MinioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Service
public class FileService {
    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private MinioService minioService;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    private static final Map<String, List<String>> ALLOWED_TYPES = new HashMap<>();
    static {
        ALLOWED_TYPES.put("jpg", Arrays.asList("image/jpeg", "image/jpg"));
        ALLOWED_TYPES.put("jpeg", Arrays.asList("image/jpeg", "image/jpg"));
        ALLOWED_TYPES.put("png", Arrays.asList("image/png"));
        ALLOWED_TYPES.put("gif", Arrays.asList("image/gif"));
        ALLOWED_TYPES.put("bmp", Arrays.asList("image/bmp"));
        ALLOWED_TYPES.put("pdf", Arrays.asList("application/pdf"));
        ALLOWED_TYPES.put("doc", Arrays.asList("application/msword"));
        ALLOWED_TYPES.put("docx", Arrays.asList("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
    }

    public String storeFile(MultipartFile file) throws IOException {
        log.debug("Сохранение файла в MinIO: {}", file.getOriginalFilename());

        // Проверка пустого файла
        if (file.isEmpty()) {
            log.warn("Попытка загрузки пустого файла");
            throw new IOException("Файл пустой");
        }

        // Проверка размера файла
        if (file.getSize() > MAX_FILE_SIZE) {
            log.warn("Размер файла превышает 5MB: {} байт", file.getSize());
            throw new IOException("Размер файла превышает 5MB");
        }

        // Проверка расширения файла
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName).toLowerCase();

        if (!ALLOWED_TYPES.containsKey(fileExtension)) {
            log.warn("Недопустимое расширение файла: {}", fileExtension);
            throw new IOException("Недопустимый тип файла. Разрешены: " + String.join(", ", ALLOWED_TYPES.keySet()));
        }

        // Проверка MIME типа
        String mimeType = file.getContentType();
        List<String> allowedMimeTypes = ALLOWED_TYPES.get(fileExtension);

        if (mimeType == null || !allowedMimeTypes.contains(mimeType.toLowerCase())) {
            log.warn("Недопустимый MIME тип: {}, ожидался один из: {}", mimeType, allowedMimeTypes);
            throw new IOException("Недопустимый MIME тип. Ожидалось: " +
                allowedMimeTypes + ", получено: " + mimeType);
        }

        // Проверка "магических чисел" для изображений
        if (fileExtension.matches("jpg|jpeg|png|gif|bmp")) {
            if (!isValidImageFile(file.getBytes())) {
                log.warn("Файл не является валидным изображением: {}", originalFileName);
                throw new IOException("Файл не является валидным изображением");
            }
        }

        try {
            String fileName = minioService.uploadFile(file);
            log.info("Файл успешно сохранен в MinIO: {}, размер: {} байт", fileName, file.getSize());
            return fileName;
        } catch (Exception e) {
            log.error("Ошибка при сохранении файла в MinIO: {}", e.getMessage(), e);
            throw new IOException("Ошибка при сохранении файла в MinIO: " + e.getMessage(), e);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        log.warn("Не удалось определить расширение файла: {}", fileName);
        return "";
    }

    private boolean isValidImageFile(byte[] fileBytes) {
        if (fileBytes.length < 4) return false;

        // PNG
        if (fileBytes[0] == (byte) 0x89 && fileBytes[1] == (byte) 0x50 &&
            fileBytes[2] == (byte) 0x4E && fileBytes[3] == (byte) 0x47) {
            return true;
        }

        // JPEG
        if (fileBytes[0] == (byte) 0xFF && fileBytes[1] == (byte) 0xD8 &&
            fileBytes[2] == (byte) 0xFF) {
            return true;
        }

        // GIF
        if (fileBytes[0] == (byte) 0x47 && fileBytes[1] == (byte) 0x49 &&
            fileBytes[2] == (byte) 0x46) {
            return true;
        }

        // BMP
        if (fileBytes[0] == (byte) 0x42 && fileBytes[1] == (byte) 0x4D) {
            return true;
        }

        return false;
    }

    public boolean deleteFile(String fileName) {
        log.debug("Удаление файла из MinIO: {}", fileName);

        try {
            minioService.deleteFile(fileName);
            log.info("Файл успешно удален из MinIO: {}", fileName);
            return true;
        } catch (Exception e) {
            log.error("Ошибка удаления файла из MinIO: {}, ошибка: {}", fileName, e.getMessage(), e);
            return false;
        }
    }
}