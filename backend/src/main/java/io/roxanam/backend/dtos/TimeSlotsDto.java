package io.roxanam.backend.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class TimeSlotsDto {
    private Long salonId;
    private Long employeeId;
    private Instant date;
}
