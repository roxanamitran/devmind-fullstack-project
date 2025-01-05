package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.SalonDto;
import io.roxanam.backend.dtos.UserDto;
import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.mappers.SalonMapper;
import io.roxanam.backend.mappers.UserMapper;
import io.roxanam.backend.services.SalonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/salons")
@AllArgsConstructor
public class SalonController {
    private final SalonService salonService;

    @GetMapping
    public List<SalonDto> getAll() {
        List<Salon> salons = salonService.findAll();
        return salons
                .stream()
                .map(SalonMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalonDto save(@RequestBody SalonDto salonDto) {
        return SalonMapper.toDto(salonService.save(salonDto));
    }

    @PostMapping("/{salonId}/employee/{employeeId}")
    public SalonDto addEmployee(@PathVariable("salonId") Integer salonId, @PathVariable("employeeId") Integer employeeId) {
        return SalonMapper.toDto(salonService.addEmployee(salonId, employeeId));
    }

    @GetMapping("/{id}")
    public SalonDto getById(@PathVariable("id") Integer id) {
        return SalonMapper.toDto(salonService.findById(id));
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Integer id) {
         return salonService.deleteById(id);
    }

    @PutMapping("/{id}")
    public SalonDto update(@PathVariable("id") Integer id, @RequestBody SalonDto salonDto) {
        if (id != salonDto.getId()) {
            salonDto.setId(id);
        }

        return SalonMapper.toDto(salonService.save(salonDto));
    }
}
