package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.HolidayDto;
import io.roxanam.backend.entities.Holiday;

public class HolidayMapper {

    public static Holiday toEntity(HolidayDto dto) {
        if (dto == null) {
            return null;
        }

        Holiday entity = new Holiday();
        entity.setId(dto.getId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setEmployee(UserMapper.toEntity(dto.getEmployee()));

        return entity;
    }

    public static HolidayDto toDto(Holiday entity) {
        if (entity == null) {
            return null;
        }

        HolidayDto dto = new HolidayDto();
        dto.setId(entity.getId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setEmployee(UserMapper.toDto(entity.getEmployee()));

        return dto;
    }
}
