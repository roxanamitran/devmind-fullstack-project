package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.AppointmentDto;
import io.roxanam.backend.dtos.TimeSlotsDto;
import io.roxanam.backend.dtos.UserDto;
import io.roxanam.backend.entities.Appointment;
import io.roxanam.backend.entities.AppointmentStatus;
import io.roxanam.backend.entities.User;
import io.roxanam.backend.entities.UserType;
import io.roxanam.backend.mappers.AppointmentMapper;
import io.roxanam.backend.services.AppointmentService;
import io.roxanam.backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;
    private UserService userService;

    @PostMapping
    public AppointmentDto save(@RequestBody AppointmentDto appointmentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customer = userService.findByEmail(authentication.getName());

        UserDto customerDto = new UserDto();
        customerDto.setId(customer.getId());

        appointmentDto.setCustomer(customerDto);

        return AppointmentMapper.toDto((appointmentService.save(AppointmentMapper.toEntity(appointmentDto))));
    }

    @GetMapping
    public List<AppointmentDto> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        List<Appointment> appointments = new ArrayList<>();
        if (authorities.contains("ROLE_" + UserType.CUSTOMER)) {
            appointments = appointmentService.findAllByCustomerEmail(authentication.getName());
        } else if (authorities.contains("ROLE_" + UserType.EMPLOYEE)) {
            appointments = appointmentService.findAllByEmployeeEmail(authentication.getName());
        } else if (authorities.contains("ROLE_" + UserType.MANAGER)) {
            appointments = appointmentService.findAllByManagerEmail(authentication.getName());
        }

        return appointments.stream().map(AppointmentMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AppointmentDto findById(@PathVariable("id") Long id) {
        return AppointmentMapper.toDto(appointmentService.findById(id));
    }


    @GetMapping("/by-employee/{id}")
    public List<AppointmentDto> findAllByEmployee(@PathVariable("id") Long employeeId) {
        List<Appointment> appointments = appointmentService.findAllByEmployeeId(employeeId);
        return appointments.stream()
                .map(a -> AppointmentMapper.toDto(a))
                .collect(Collectors.toList());
    }

    @GetMapping("/by-customer/{id}")
    public List<AppointmentDto> findAllByCustomer(@PathVariable("id") Long customerId) {
        List<Appointment> appointments = appointmentService.findAllByCustomerId(customerId);
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
    public AppointmentDto status(@PathVariable("id") Long id, @PathVariable("status") AppointmentStatus appointmentStatus) {
        return AppointmentMapper.toDto(appointmentService.changeStatus(id, appointmentStatus));
    }

    @PostMapping("/available-slots")
    public List<LocalTime> getAvailableSlots(@RequestBody TimeSlotsDto timeSlots) {
        return appointmentService.calculateAvailableTimeslots(timeSlots);
    }


}
