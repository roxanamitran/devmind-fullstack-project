package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.ServiceTypeDto;
import io.roxanam.backend.mappers.ServiceTypeMapper;
import io.roxanam.backend.services.ServiceTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @GetMapping("/{id}")
    public ServiceTypeDto findById(@PathVariable("id") Integer id) {
        return ServiceTypeMapper.toDto(serviceTypeService.findById(id));
    }

    @GetMapping
    public List<ServiceTypeDto> findAll() {
        return serviceTypeService.findAll()
                .stream()
                .map(ServiceTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/salon/{salonId}")
    public List<ServiceTypeDto> findAllBySalonId(@PathVariable("salonId") Integer salonId) {
        return serviceTypeService.findAllBySalonId(salonId)
                .stream()
                .map(ServiceTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ServiceTypeDto save(@RequestBody ServiceTypeDto serviceTypeDto) {
        return ServiceTypeMapper.toDto(serviceTypeService.save(serviceTypeDto));
    }

    @PostMapping("/{serviceTypeId}/employees/{employeeId}")
    public ServiceTypeDto linkEmployeeToServiceType(@PathVariable("serviceTypeId") Integer serviceTypeId, @PathVariable("employeeId") Integer employeeId) {
        return ServiceTypeMapper.toDto(serviceTypeService.linkEmployeeToServiceType(employeeId, serviceTypeId));
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Integer id) {
        return serviceTypeService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ServiceTypeDto update(@PathVariable("id") Integer id, @RequestBody ServiceTypeDto serviceTypeDto) {
        if (id != serviceTypeDto.getId()) {
            id = serviceTypeDto.getId();
        }

        return ServiceTypeMapper.toDto(serviceTypeService.save(serviceTypeDto));
    }
}
