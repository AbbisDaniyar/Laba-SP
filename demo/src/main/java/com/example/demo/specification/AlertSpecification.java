package com.example.demo.specification;

import org.springframework.data.jpa.domain.Specification;
import com.example.demo.model.Alert;
import com.example.demo.model.StatusType;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс спецификаций для фильтрации оповещений.
 * Предоставляет статические методы для создания спецификаций,
 * используемых при построении сложных запросов к базе данных.
 */
public class AlertSpecification {

    /**
     * Создает спецификацию для фильтрации оповещений по статусу.
     *
     * @param status Статус оповещения для фильтрации (может быть null)
     * @return Спецификация для использования в запросах
     */
    public static Specification<Alert> hasStatus(StatusType status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    /**
     * Создает спецификацию для фильтрации оповещений по ID автобуса.
     *
     * @param busId ID автобуса для фильтрации (может быть null)
     * @return Спецификация для использования в запросах
     */
    public static Specification<Alert> hasBusId(Long busId) {
        return (root, query, criteriaBuilder) -> {
            if (busId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("busId"), busId);
        };
    }

    /**
     * Создает спецификацию для фильтрации оповещений по местоположению (частичное совпадение).
     *
     * @param location Местоположение для фильтрации (может быть null)
     * @return Спецификация для использования в запросах
     */
    public static Specification<Alert> locationContains(String location) {
        return (root, query, criteriaBuilder) -> {
            if (location == null || location.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("location")),
                "%" + location.toLowerCase().trim() + "%"
            );
        };
    }

    /**
     * Создает комплексную спецификацию для фильтрации оповещений по нескольким параметрам.
     *
     * @param status Статус оповещения для фильтрации (может быть null)
     * @param busId ID автобуса для фильтрации (может быть null)
     * @param location Местоположение для фильтрации (может быть null)
     * @return Комплексная спецификация для использования в запросах
     */
    public static Specification<Alert> filter(StatusType status, Long busId, String location) {
        return Specification.where(hasStatus(status))
                           .and(hasBusId(busId))
                           .and(locationContains(location));
    }
}
