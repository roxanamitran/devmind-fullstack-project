package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.ScheduleDto;
import io.roxanam.backend.entities.Schedule;

public class ScheduleMapper {

    public static Schedule toEntity(ScheduleDto dto) {
        if (dto == null) {
            return null;
        }

        Schedule entity = new Schedule();
        entity.setId(dto.getId());
        entity.setDay(dto.getDay());
        entity.setStartHour(dto.getStartHour());
        entity.setEndHour(dto.getEndHour());

        return entity;
    }

    public static ScheduleDto toDto(Schedule entity) {
        if(entity == null) {
            return null;
        }

        ScheduleDto dto = new ScheduleDto();
        dto.setId(entity.getId());
        dto.setDay(entity.getDay());
        dto.setStartHour(entity.getStartHour());
        dto.setEndHour(entity.getEndHour());

        return dto;
    }
}
