package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.HolidayDto;
import io.roxanam.backend.entities.Holiday;
import io.roxanam.backend.mappers.HolidayMapper;
import io.roxanam.backend.services.HolidayService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/holidays")
public class HolidayController {

    private HolidayService holidayService;


    @PostMapping
    public HolidayDto save(@RequestBody HolidayDto holidayDto) {
        return HolidayMapper.toDto(holidayService.save(HolidayMapper.toEntity(holidayDto)));
    }

    @GetMapping("/{id}")
    public HolidayDto findById(@PathVariable("id") Long id) {
        return HolidayMapper.toDto(holidayService.findById(id));
    }

    @GetMapping
    public List<HolidayDto> findAll() {
        List<Holiday> holidays= holidayService.findAll();

        return holidays.stream().map(h -> HolidayMapper.toDto(h)).collect(Collectors.toList());
    }

    @GetMapping("/by-employee/{id}")
    public List<HolidayDto> findAllByEmployee(@PathVariable("id") Long employeeId) {
        List<Holiday> holidays = holidayService.findAllByEmployee(employeeId);
        return holidays.stream()
                .map(h -> HolidayMapper.toDto(h))
                .collect(Collectors.toList());
    }
}
