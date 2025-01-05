package io.roxanam.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.roxanam.backend.entities.UserType;
import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phoneNumber;
    private UserType userType;
}
