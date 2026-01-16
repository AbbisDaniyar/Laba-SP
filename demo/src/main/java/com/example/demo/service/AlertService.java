package com.example.demo.service;

import com.example.demo.model.Alert;
import com.example.demo.model.StatusType;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


/**
 * Сервис для управления оповещениями.
 * Предоставляет методы для выполнения операций CRUD над оповещениями,
 * а также дополнительные методы для обновления статуса и назначения пользователей.
 */
public interface AlertService {

    /**
     * Находит все оповещения.
     * Результат кэшируется с использованием Spring Cache.
     *
     * @return Список всех оповещений
     */
    @Cacheable(value = "alerts", unless = "#result.isEmpty()")
    List<Alert> findAll();


    /**
     * Находит оповещения по статусу.
     * Результат кэшируется с использованием Spring Cache.
     *
     * @param status Статус оповещений для поиска
     * @return Список оповещений с указанным статусом
     */
    @Cacheable(value = "alertsByStatus", key = "#status.name()")
    List<Alert> findByStatus(StatusType status);


    /**
     * Находит оповещение по его ID.
     *
     * @param id Уникальный идентификатор оповещения
     * @return Объект Optional, содержащий оповещение если найдено, иначе пустой Optional
     */
    Optional<Alert> findById(Long id);


    /**
     * Создает новое оповещение.
     * После создания очищает соответствующие кэши.
     *
     * @param alert Объект оповещения для создания
     * @return Созданное оповещение
     */
    @Transactional
    @CacheEvict(value = {"alerts", "alertsByStatus", "alertsByBus", "alertsByUser"}, allEntries = true)
    Alert create(Alert alert);


    /**
     * Обновляет статус оповещения.
     * После обновления очищает соответствующие кэши.
     *
     * @param alertId Уникальный идентификатор оповещения для обновления
     * @param newStatus Новый статус для оповещения
     * @return Обновленное оповещение
     */
    @Transactional
    @CacheEvict(value = {"alerts", "alertsByStatus", "alertsByBus", "alertsByUser"}, allEntries = true)
    Alert updateStatus(Long alertId, StatusType newStatus);


    /**
     * Назначает оповещение пользователю.
     * После назначения очищает соответствующие кэши.
     *
     * @param alertId Уникальный идентификатор оповещения для назначения
     * @param userId Уникальный идентификатор пользователя, которому назначается оповещение
     * @return Обновленное оповещение
     */
    @Transactional
    @CacheEvict(value = {"alerts", "alertsByStatus", "alertsByBus", "alertsByUser"}, allEntries = true)
    Alert assignToUser(Long alertId, Long userId);


    /**
     * Удаляет оповещение по его ID.
     * После удаления очищает соответствующие кэши.
     *
     * @param id Уникальный идентификатор оповещения для удаления
     */
    @Transactional
    @CacheEvict(value = {"alerts", "alertsByStatus", "alertsByBus", "alertsByUser"}, allEntries = true)
    void deleteById(Long id);

    /**
     * Добавляет файл к оповещению.
     *
     * @param alertId Уникальный идентификатор оповещения
     * @param filePath Путь к файлу для добавления
     * @return Обновленное оповещение с добавленным файлом
     */
    Alert addFileToAlert(Long alertId, String filePath);
}