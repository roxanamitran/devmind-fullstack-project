package io.roxanam.backend.services;

import io.roxanam.backend.dtos.SalonDto;
import io.roxanam.backend.dtos.UserDto;
import io.roxanam.backend.entities.User;
import io.roxanam.backend.exceptions.EntityNotFoundException;
import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.repositories.SalonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SalonService {

    private final SalonRepository salonRepository;
    private final UserService userService;

    public List<Salon> findAll() {
        return salonRepository.findAll();
    }

    public Salon findById(Integer id) {
        return salonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salon with id " + id + " not found"));
    }


    public Salon save(Salon salon) {
        return salonRepository.save(salon);
    }

    public Salon save(SalonDto dto) {
        Salon salon = new Salon();
        salon.setId(salon.getId());
        salon.setManager(userService.findById(dto.getManager().getId()));
        salon.setName(dto.getName());
        salon.setEmail(dto.getEmail());
        salon.setAddress(dto.getAddress());
        salon.setPhone(dto.getPhone());
        salon.setPhoto(dto.getPhoto());
        salon.setOpenHour(dto.getOpenHour());
        salon.setCloseHour(dto.getCloseHour());
        salon.setDescription(dto.getDescription());

        return salonRepository.save(salon);
    }

    public String deleteById(Integer id) {
        salonRepository.deleteById(id);

        return "Salon deleted successfully";
    }

    public Salon addEmployee(Integer salonId, Integer employeeId) {
        Salon salon = findById(salonId);
        User employee = userService.findById(employeeId);
        salon.getEmployees().add(employee);

        return save(salon);
    }
}
