package io.roxanam.backend.controllers;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import io.roxanam.backend.dtos.SalonDto;
import io.roxanam.backend.dtos.UserDto;
import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.mappers.SalonMapper;
import io.roxanam.backend.mappers.UserMapper;
import io.roxanam.backend.services.SalonService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/salons")
@AllArgsConstructor
public class SalonController {
    private SalonService salonService;

    @PostMapping
    public Salon save(@RequestBody SalonDto dto) {
        return salonService.save(SalonMapper.toEntity(dto), SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping
    public List<SalonDto> findAll(@RequestParam(required = false) String salonOffer) {
        List <Salon> salons = salonService.findAll(salonOffer);

        return salons.stream().map(SalonMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SalonDto findById(@PathVariable("id") Long id) {
        return SalonMapper.toDto(salonService.findById(id));
    }

    @PutMapping("/{id}")
    public SalonDto update(@PathVariable("id") Long id, @RequestBody SalonDto salonDto) {
        if (id != salonDto.getId()) {
            salonDto.setId(id);
        }

        return SalonMapper.toDto(salonService.update(SalonMapper.toEntity(salonDto)));
    }

    @GetMapping("/{salonId}/employees")
    public List<UserDto> getEmployees(@PathVariable("salonId") Long salondId) {
        Salon salon = salonService.findById(salondId);
        return salon.getEmployees().stream().map(UserMapper::toDto).toList();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        salonService.deleteById(id);

        return "Salon deleted.";
    }

    @PostMapping("/{salonId}/assign/{employeeId}")
    public void assignEmployeeToSalon(@PathVariable("salonId") Long salonId, @PathVariable("employeeId") Long employeeId) {
        salonService.assignEmployeeToSalon(salonId, employeeId);
    }

    @DeleteMapping("/{salonId}/remove/{employeeId}")
    public void removeEmployeeToSalon(@PathVariable("salonId") Long salonId, @PathVariable("employeeId") Long employeeId) {
        salonService.removeEmployeeFromSalon(salonId, employeeId);
    }
}
