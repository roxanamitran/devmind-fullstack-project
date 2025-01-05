package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.AppointmentDto;
import io.roxanam.backend.entities.Appointment;

public class AppointmentMapper {
    public static AppointmentDto toDto(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();

        appointmentDto.setId(appointment.getId());
        appointmentDto.setStartDate(appointment.getStartDate());
        appointmentDto.setEndDate(appointment.getEndDate());
        appointmentDto.setStatus(appointment.getStatus());
        appointmentDto.setClient(UserMapper.toDto(appointment.getClient()));
        appointmentDto.setEmployee(UserMapper.toDto(appointment.getEmployee()));
        appointmentDto.setSalon(SalonMapper.toDto(appointment.getSalon()));
        appointmentDto.setServiceType(ServiceTypeMapper.toDto(appointment.getServiceType()));

        return appointmentDto;
    }
}
