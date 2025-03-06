package io.roxanam.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

@Data
public class TimeSlotsDto {
    private Long salonId;
    private Long employeeId;
    private Integer duration;
    private Instant date;
}
