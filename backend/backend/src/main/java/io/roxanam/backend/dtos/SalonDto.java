package io.roxanam.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalonDto {
    private Integer id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String photo;
    private String description;
    private Instant openHour;
    private Instant closeHour;
    private UserDto manager;
    private List<UserDto> employees;
}
