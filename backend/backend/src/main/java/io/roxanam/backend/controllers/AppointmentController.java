package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.AppointmentDto;
import io.roxanam.backend.mappers.AppointmentMapper;
import io.roxanam.backend.services.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    public AppointmentDto findById(@PathVariable("id") Integer id) {
        return AppointmentMapper.toDto(appointmentService.findById(id));
    }

    @GetMapping("/client/{id}")
    public List<AppointmentDto> findAllByClientId(@PathVariable("id") Integer clientId) {
        return appointmentService.findAllByClientId(clientId)
                .stream()
                .map(AppointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{id}")
    public List<AppointmentDto> findAllByEmployeeId(@PathVariable("id") Integer emloyeeId) {
        return appointmentService.findAllByEmployeeId(emloyeeId)
                .stream()
                .map(AppointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public AppointmentDto save(@RequestBody AppointmentDto appointmentDto) {
        return AppointmentMapper.toDto(appointmentService.save(appointmentDto));
    }

    @PutMapping("/{id}")
    public AppointmentDto update(@PathVariable("id") Integer id, @RequestBody AppointmentDto appointmentDto) {
        if (id != appointmentDto.getId()) {
            appointmentDto.setId(id);
        }

        return AppointmentMapper.toDto(appointmentService.save(appointmentDto));
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Integer id) {
        return appointmentService.deleteById(id);
    }
}
