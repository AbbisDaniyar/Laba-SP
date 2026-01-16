package com.example.demo.exception;

/**
 * Исключение, которое выбрасывается, когда автобус с указанным ID не найден.
 * Является наследником RuntimeException и используется для обработки ситуаций,
 * когда запрашиваемый автобус отсутствует в системе.
 */
public class BusNotFoundException extends RuntimeException {
    /**
     * Конструктор исключения с указанием ID не найденного автобуса.
     *
     * @param id ID автобуса, который не был найден
     */
    public BusNotFoundException(Long id) {
        super("Автобус с ID " + id + " не найден");
    }

    /**
     * Конструктор исключения с произвольным сообщением об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public BusNotFoundException(String message) {
        super(message);
    }
}