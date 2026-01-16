package com.example.demo.repository;

import com.example.demo.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью Bus (автобус).
 * Предоставляет методы для поиска, фильтрации и управления автобусами.
 */
@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    /**
     * Находит список автобусов, модель которых содержит указанную строку (без учета регистра).
     *
     * @param model часть названия модели для поиска
     * @return список автобусов с моделями, содержащими указанную строку
     */
    List<Bus> findByModelContainingIgnoreCase(String model);

    /**
     * Проверяет, существует ли автобус с указанной моделью.
     *
     * @param model модель автобуса для проверки
     * @return true, если автобус с такой моделью существует, иначе false
     */
    boolean existsByModel(String model);
}