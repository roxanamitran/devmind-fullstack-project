package io.roxanam.backend.dtos;

import io.roxanam.backend.entities.Day;
import lombok.Data;

@Data
public class ScheduleDto {
    private Long id;
    private Day day;
    private String startHour;
    private String endHour;
}
