package io.roxanam.backend.services;

import io.roxanam.backend.entities.Holiday;
import io.roxanam.backend.entities.User;
import io.roxanam.backend.repositories.HolidayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HolidayService {

    private HolidayRepository holidayRepository;
    private UserService userService;

    public Holiday save(Holiday holiday) {
        holiday.setEmployee(userService.findById(holiday.getEmployee().getId()));
        return holidayRepository.save(holiday);
    }

    public Holiday findById(Long id) {
        return holidayRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Holiday with id " + id + " not found"));
    }

    public List<Holiday> findAll() {
        return holidayRepository.findAll();
    }

    public List<Holiday> findAllByEmployee(Long userId) {
        User foundedEmployee = userService.findById(userId);
        return holidayRepository.findAllByEmployeeAndIsActiveTrue(foundedEmployee);

    }
}
