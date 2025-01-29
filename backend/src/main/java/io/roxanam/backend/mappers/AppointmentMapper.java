package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.AppointmentDto;
import io.roxanam.backend.entities.Appointment;

public class AppointmentMapper {

    public static Appointment toEntity(AppointmentDto dto) {
        if (dto == null) {
            return null;
        }

        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setStatus(dto.getStatus());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setSalon(SalonMapper.toEntity(dto.getSalon()));
        entity.setSalonToSalonOffer(SalonToSalonOfferMapper.toEntity(dto.getSalonToSalonOffer()));
        entity.setCustomer(UserMapper.toEntity(dto.getCustomer()));
        entity.setEmployee(UserMapper.toEntity(dto.getEmployee()));

        return entity;
    }


    public static AppointmentDto toDto(Appointment entity) {
        if(entity == null) {
            return null;
        }

        AppointmentDto dto = new AppointmentDto();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setSalon(SalonMapper.toDto(entity.getSalon()));
        dto.setSalonToSalonOffer(SalonToSalonOfferMapper.toDto(entity.getSalonToSalonOffer()));
        dto.setCustomer(UserMapper.toDto(entity.getCustomer()));
        dto.setEmployee(UserMapper.toDto(entity.getEmployee()));

        return dto;
    }
}
