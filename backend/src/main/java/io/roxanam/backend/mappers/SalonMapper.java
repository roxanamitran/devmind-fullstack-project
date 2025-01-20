package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.SalonDto;
import io.roxanam.backend.entities.Salon;

import java.util.stream.Collectors;

public class SalonMapper {
    public static Salon toEntity(SalonDto dto) {
        if (dto == null) {
            return null;
        }

        Salon entity = new Salon();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPhotoUrl(dto.getPhotoUrl());
        entity.setDescription(dto.getDescription());
        entity.setManager(UserMapper.toEntity(dto.getManager()));
        entity.setSchedules(dto.getSchedules().stream().map(ScheduleMapper::toEntity).collect(Collectors.toList()));

        return entity;
    }

    public static SalonDto toDto(Salon entity) {
        if (entity == null) {
            return  null;
        }

        SalonDto dto = new SalonDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setPhotoUrl(entity.getPhotoUrl());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setDescription(entity.getDescription());
        dto.setManager(UserMapper.toDto(entity.getManager()));
        dto.setSchedules(entity.getSchedules().stream().map(ScheduleMapper::toDto).collect(Collectors.toList()));

        return dto;
    }
}
