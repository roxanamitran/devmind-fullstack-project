package io.roxanam.backend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ServiceTypeDto {
    private Integer id;
    private String name;
    private String photo;
    private Double price;
    private Integer duration;
    private SalonDto salon;
    private List<UserDto> employees;
}
