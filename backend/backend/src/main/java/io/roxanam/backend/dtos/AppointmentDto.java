package io.roxanam.backend.dtos;

import io.roxanam.backend.entities.AppointmentStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class AppointmentDto {
    private Integer id;
    private AppointmentStatus status;
    private Instant startDate;
    private Instant endDate;
    private ServiceTypeDto serviceType;
    private SalonDto salon;
    private UserDto client;
    private UserDto employee;
}
