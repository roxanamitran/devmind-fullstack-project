package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.ServiceTypeDto;
import io.roxanam.backend.entities.ServiceType;

import java.util.stream.Collectors;

public class ServiceTypeMapper {

    public static ServiceTypeDto toDto(ServiceType serviceType) {
        ServiceTypeDto serviceTypeDto = new ServiceTypeDto();
        serviceTypeDto.setName(serviceType.getName());
        serviceTypeDto.setPhoto(serviceType.getPhoto());
        serviceTypeDto.setPrice(serviceType.getPrice());
        serviceTypeDto.setDuration(serviceType.getDuration());
        serviceTypeDto.setId(serviceType.getId());
        serviceTypeDto.setSalon(SalonMapper.toDto(serviceType.getSalon()));
        serviceTypeDto.setEmployees(serviceType.getEmployees()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList()));

        return serviceTypeDto;
    }


}
