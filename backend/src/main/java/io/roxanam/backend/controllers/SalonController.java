package io.roxanam.backend.controllers;


import io.roxanam.backend.dtos.SalonDto;
import io.roxanam.backend.dtos.UserDto;
import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.exceptions.SalonNotFoundException;
import io.roxanam.backend.mappers.SalonMapper;
import io.roxanam.backend.mappers.UserMapper;
import io.roxanam.backend.services.SalonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/search/by-logged-in-manager")
    public ResponseEntity findByLoggedInManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return ResponseEntity.ok(SalonMapper.toDto(salonService.findByManagerEmail(authentication.getName())));
        } catch (SalonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
