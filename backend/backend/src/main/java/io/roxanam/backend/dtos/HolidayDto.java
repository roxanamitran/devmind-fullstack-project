package io.roxanam.backend.dtos;

import io.roxanam.backend.entities.User;
import lombok.Data;

import java.time.Instant;

@Data
public class HolidayDto {
    private Integer id;
    private Instant startDate;
    private Instant endDate;

    private UserDto employee;
}
