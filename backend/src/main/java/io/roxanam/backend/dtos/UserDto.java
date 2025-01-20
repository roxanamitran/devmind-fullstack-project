package io.roxanam.backend.dtos;

import io.roxanam.backend.entities.UserType;
import lombok.Data;


@Data
public class UserDto {
    private Long id;
    private UserType userType;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean isActive;
}
