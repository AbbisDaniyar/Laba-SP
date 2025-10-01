package com.example.demo.service;

import com.example.demo.model.Alert;
import com.example.demo.model.StatusType;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


public interface AlertService {
    // Получить все
    @Cacheable(value = "alert", key = "#root.methodName")
    List<Alert> findAll();
    
    // Найти инциденты по статусу
    @Cacheable(value = "alert", key = "#status")
    List<Alert> findByStatus(StatusType status);
    
    // Найти инцидент по ID
    @Cacheable(value = "alert", key = "#id")
    Optional<Alert> findById(Long id);
    
    // Создать новый инцидент
    @Transactional
    @CacheEvict(value = {"alerts"}, allEntries = true)
    Alert create(Alert alert);
    
    // Обновить статус инцидента
    @Transactional
    @CacheEvict(value = {"alerts", "alert"}, allEntries = true)
    Alert updateStatus(Long alertId, StatusType newStatus);
    
    // Назначить инцидент на пользователя
    @Transactional
    @CacheEvict(value = {"alerts", "alert"}, allEntries = true)
    Alert assignToUser(Long alertId, Long userId);
    
    // Удалить инцидент
    @Transactional
    @CacheEvict(value = {"alerts", "alert"}, allEntries = true)
    void deleteById(Long id);

    
}
