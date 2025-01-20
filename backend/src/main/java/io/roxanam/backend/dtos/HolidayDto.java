package io.roxanam.backend.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class HolidayDto {
    private Long id;
    private Instant startDate;
    private Instant endDate;
    private UserDto employee;
    private boolean isActive;
}
