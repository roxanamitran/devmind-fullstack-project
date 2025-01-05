package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.HolidayDto;
import io.roxanam.backend.entities.Holiday;
import io.roxanam.backend.entities.User;
import io.roxanam.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
@AllArgsConstructor
public class HolidayMapper {

    private static UserRepository userRepository ;


    public static HolidayDto toDto(Holiday holiday) {
        HolidayDto holidayDto = new HolidayDto();
        holidayDto.setId(holiday.getId());
        holidayDto.setStartDate(holiday.getStartDate());
        holidayDto.setEndDate(holiday.getEndDate());
        holidayDto.setEmployee(UserMapper.toDto(holiday.getEmployee()));

        return holidayDto;
    }
}
