package com.example.demo.exception;

/**
 * Исключение, которое выбрасывается, когда инцидент с указанным ID не найден.
 * Является наследником RuntimeException и используется для обработки ситуаций,
 * когда запрашиваемый инцидент отсутствует в системе.
 */
public class AlertNotFoundException extends RuntimeException {
    /**
     * Конструктор исключения с указанием ID не найденного инцидента.
     *
     * @param id ID инцидента, который не был найден
     */
    public AlertNotFoundException(Long id) {
        super("Инцидент с ID " + id + " не найден");
    }
}