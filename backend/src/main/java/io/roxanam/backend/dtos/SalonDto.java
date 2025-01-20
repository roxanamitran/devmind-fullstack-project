package io.roxanam.backend.dtos;

import lombok.Data;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class SalonDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String photoUrl;
    private String description;
    private UserDto manager;
    private List<ScheduleDto> schedules = new ArrayList<>();
}
