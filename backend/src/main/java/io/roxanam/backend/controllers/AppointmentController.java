package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.AppointmentDto;
import io.roxanam.backend.dtos.TimeSlotsDto;
import io.roxanam.backend.entities.Appointment;
import io.roxanam.backend.entities.AppointmentStatus;
import io.roxanam.backend.mappers.AppointmentMapper;
import io.roxanam.backend.services.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;

    @PostMapping
    public AppointmentDto save(@RequestBody AppointmentDto appointmentDto) {
        return AppointmentMapper.toDto((appointmentService.save(AppointmentMapper.toEntity(appointmentDto))));
    }

    @GetMapping
    public List<AppointmentDto> findAll() {
        List<Appointment> appointments = appointmentService.findAll();
        return appointments.stream().map(a -> AppointmentMapper.toDto(a)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AppointmentDto findById(@PathVariable("id") Long id) {
        return AppointmentMapper.toDto(appointmentService.findById(id));
    }


    @GetMapping("/by-employee/{id}")
    public List<AppointmentDto> findAllByEmployee(@PathVariable("id") Long employeeId) {
        List<Appointment> appointments = appointmentService.findAllByEmployee(employeeId);
        return appointments.stream()
                .map(a -> AppointmentMapper.toDto(a))
                .collect(Collectors.toList());
    }

    @GetMapping("/by-customer/{id}")
    public List<AppointmentDto> findAllByCustomer(@PathVariable("id") Long customerId) {
        List<Appointment> appointments = appointmentService.findAllByCustomer(customerId);
        return appointments.stream()
                .map(a -> AppointmentMapper.toDto(a))
                .collect(Collectors.toList());
    }

    @GetMapping("/by-salon/{id}")
    public List<AppointmentDto> findAllBySalon(@PathVariable("id") Long salonId) {
        List<Appointment> appointments = appointmentService.findAllBySalon(salonId);
        return appointments.stream()
                .map(a -> AppointmentMapper.toDto(a))
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/{status}")
    public AppointmentDto accept(@PathVariable("id") Long id, @PathVariable("status")AppointmentStatus appointmentStatus) {
        return AppointmentMapper.toDto(appointmentService.changeStatus(id, appointmentStatus));
    }

    @PostMapping("/available-slots")
    public List<LocalTime> getAvailableSlots(@RequestBody TimeSlotsDto timeSlots) {
        return appointmentService.calculateAvailableTimeslots(timeSlots);
    }


}
