package com.example.demo.repository;

import com.example.demo.model.Alert;
import com.example.demo.model.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью Alert (инцидент).
 * Предоставляет методы для поиска, фильтрации и управления инцидентами.
 */
@Repository
public interface AlertRepository extends JpaRepository<Alert, Long>, JpaSpecificationExecutor<Alert> {

    /**
     * Находит список инцидентов по статусу.
     *
     * @param status статус инцидентов для поиска
     * @return список инцидентов с указанным статусом
     */
    List<Alert> findByStatus(StatusType status);

    /**
     * Находит список инцидентов по ID автобуса.
     *
     * @param busId ID автобуса для поиска
     * @return список инцидентов, связанных с указанным автобусом
     */
    List<Alert> findByBusId(Long busId);

    /**
     * Находит список инцидентов по ID назначенного пользователя.
     *
     * @param userId ID пользователя, которому назначены инциденты
     * @return список инцидентов, назначенных указанному пользователю
     */
    List<Alert> findByAssignedToUserId(Long userId);

    /**
     * Находит список инцидентов в заданном временном диапазоне.
     *
     * @param startDate начальная дата для поиска
     * @param endDate конечная дата для поиска
     * @return список инцидентов в заданном временном диапазоне
     */
    List<Alert> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

}