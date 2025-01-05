package io.roxanam.backend.services;

import io.roxanam.backend.dtos.ServiceTypeDto;
import io.roxanam.backend.entities.ServiceType;
import io.roxanam.backend.entities.User;
import io.roxanam.backend.exceptions.EntityNotFoundException;
import io.roxanam.backend.repositories.ServiceTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;
    private final SalonService salonService;
    private final UserService userService;

    public ServiceType findById(Integer id) {
        return serviceTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service with id: " + id + " not found"));
    }

    public List<ServiceType> findAll() {
        return serviceTypeRepository.findAll();
    }

    public ServiceType save(ServiceTypeDto serviceTypeDto) {
        ServiceType entity = new ServiceType();
        entity.setName(serviceTypeDto.getName());
        entity.setPhoto(serviceTypeDto.getPhoto());
        entity.setPrice(serviceTypeDto.getPrice());
        entity.setDuration(serviceTypeDto.getDuration());
        entity.setId(serviceTypeDto.getId());
        entity.setSalon(salonService.findById(serviceTypeDto.getSalon().getId()));
        return serviceTypeRepository.save(entity);
    }

    public String deleteById(Integer id) {
        serviceTypeRepository.deleteById(id);
        return "Service deleted.";
    }

    public List<ServiceType> findAllBySalonId(Integer salonId) {
        return serviceTypeRepository.findAllBySalon_Id(salonId);
    }

    public ServiceType linkEmployeeToServiceType(Integer employeeId, Integer serviceTypeId) {
        ServiceType serviceType = findById(serviceTypeId);
        User employee = userService.findById(employeeId);
        serviceType.getEmployees().add(employee);

        return serviceTypeRepository.save(serviceType);
    }
}
