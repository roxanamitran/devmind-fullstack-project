package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.UserDto;
import io.roxanam.backend.entities.User;

public class UserMapper {

    public static User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        entity.setId(dto.getId());
        entity.setUserType(dto.getUserType());
        entity.setEmail(dto.getEmail());
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPassword(dto.getPassword());

        return entity;
    }

    public static UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUserType(entity.getUserType());
        dto.setEmail(entity.getEmail());
        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());
        dto.setPhoneNumber(entity.getPhoneNumber());

        return dto;
    }
}
