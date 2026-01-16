package com.example.demo.mapper;

import com.example.demo.dto.BusDto;
import com.example.demo.model.Bus;

/**
 * Класс маппера для преобразования между сущностью Bus и DTO BusDto.
 * Содержит статические методы для преобразования из сущности в DTO и обратно.
 */
public class BusMapper {

    /**
     * Преобразует сущность Bus в DTO BusDto.
     *
     * @param bus сущность Bus для преобразования
     * @return DTO BusDto или null, если сущность была null
     */
    public static BusDto toDto(Bus bus) {
        if (bus == null) {
            return null;
        }
        return new BusDto(
            bus.getId(),
            bus.getModel()
        );
    }

    /**
     * Преобразует DTO BusDto в сущность Bus.
     *
     * @param dto DTO BusDto для преобразования
     * @return сущность Bus или null, если DTO был null
     */
    public static Bus toEntity(BusDto dto) {
        if (dto == null) {
            return null;
        }
        Bus bus = new Bus();
        bus.setId(dto.id());
        bus.setModel(dto.model());
        return bus;
    }
}