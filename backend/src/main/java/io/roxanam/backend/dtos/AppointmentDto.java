package io.roxanam.backend.dtos;

import io.roxanam.backend.entities.AppointmentStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class AppointmentDto {
    private Long id;
    private AppointmentStatus status;
    private Instant startDate;
    private Instant endDate;
    private SalonDto salon;
    private SalonToSalonOfferDto salonToSalonOffer;
    private UserDto customer;
    private UserDto employee;
}
