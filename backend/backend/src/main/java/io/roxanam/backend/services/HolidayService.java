package io.roxanam.backend.services;

import io.roxanam.backend.dtos.HolidayDto;
import io.roxanam.backend.entities.Holiday;
import io.roxanam.backend.exceptions.EntityNotFoundException;
import io.roxanam.backend.mappers.UserMapper;
import io.roxanam.backend.repositories.HolidayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public Holiday findById(Integer id) {
        return holidayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Holiday with id: " + id + " not found"));
    }

    public List<Holiday> findAll() {
        return holidayRepository.findAll();
    }

    public Holiday save(HolidayDto holidayDto) {
        Holiday holiday = new Holiday();
        holiday.setId(holidayDto.getId());
        holiday.setStartDate(holidayDto.getStartDate());
        holiday.setEndDate(holidayDto.getEndDate());
        holiday.setEmployee(UserMapper.toEntity(holidayDto.getEmployee()));
        return holidayRepository.save(holiday);
    }

    public String deleteById(Integer id) {
        holidayRepository.deleteById(id);
        return "Holiday deleted";
    }

    public List<Holiday> findAllByEmployeeId(Integer employeeId) {
        return holidayRepository.findAllByEmployee_Id(employeeId);
    }

    public Holiday update(Integer id, Instant startDate, Instant endDate) {
        Holiday holiday = findById(id);
        holiday.setStartDate(startDate);
        holiday.setEndDate(endDate);

        return holidayRepository.save(holiday);
    }
}
