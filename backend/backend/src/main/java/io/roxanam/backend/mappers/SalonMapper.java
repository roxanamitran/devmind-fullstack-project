package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.SalonDto;
import io.roxanam.backend.entities.Salon;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SalonMapper {

    public static Salon toEntity(SalonDto dto) {
        Salon salon = new Salon();
        salon.setName(dto.getName());
        salon.setEmail(dto.getEmail());
        salon.setAddress(dto.getAddress());
        salon.setId(dto.getId());
        salon.setPhone(dto.getPhone());
        salon.setPhoto(dto.getPhoto());
        salon.setOpenHour(dto.getOpenHour());
        salon.setCloseHour(dto.getCloseHour());
        salon.setDescription(dto.getDescription());
        return salon;
    }

    public static SalonDto toDto(Salon salon) {
        SalonDto salonDto = new SalonDto();
        salonDto.setName(salon.getName());
        salonDto.setEmail(salon.getEmail());
        salonDto.setAddress(salon.getAddress());
        salonDto.setId(salon.getId());
        salonDto.setPhone(salon.getPhone());
        salonDto.setPhoto(salon.getPhoto());
        salonDto.setOpenHour(salon.getOpenHour());
        salonDto.setCloseHour(salon.getCloseHour());
        salonDto.setManager(UserMapper.toDto(salon.getManager()));
        salonDto.setEmployees(salon.getEmployees()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList()));

        return salonDto;
    }
}
