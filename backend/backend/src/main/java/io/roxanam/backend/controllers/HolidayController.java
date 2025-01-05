package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.HolidayDto;
import io.roxanam.backend.mappers.HolidayMapper;
import io.roxanam.backend.services.HolidayService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/holiday")
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping("/{id}")
    public HolidayDto findById(@PathVariable("id") Integer id) {
        return HolidayMapper.toDto(holidayService.findById(id));
    }

    @GetMapping
    public List<HolidayDto> findAll() {
        return holidayService.findAll()
                .stream()
                .map(h -> HolidayMapper.toDto(h))
                .collect(Collectors.toList());
    }

    @PostMapping
    public HolidayDto save(@RequestBody HolidayDto holidayDto) {
        return HolidayMapper.toDto(holidayService.save(holidayDto));
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Integer id) {
        return holidayService.deleteById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<HolidayDto> findAllByEmployeeId(@PathVariable("employeeId") Integer id ) {
        return holidayService.findAllByEmployeeId(id)
                .stream()
                .map(HolidayMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public HolidayDto update(@PathVariable("id") Integer id, @RequestBody Instant startDate, @RequestBody Instant endDate) {
        return HolidayMapper.toDto(holidayService.update(id, startDate, endDate));
    }


}
