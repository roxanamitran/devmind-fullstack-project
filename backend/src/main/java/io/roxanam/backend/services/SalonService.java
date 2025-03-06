package io.roxanam.backend.services;

import io.roxanam.backend.entities.*;
import io.roxanam.backend.exceptions.SalonNotFoundException;
import io.roxanam.backend.repositories.SalonRepository;
import io.roxanam.backend.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class SalonService {
    private SalonRepository salonRepository;
    private UserService userService;
    private ScheduleRepository scheduleRepository;

    public Salon save(Salon salon, String managerEmail) {
        List<Schedule> schedules = null;
        if (!CollectionUtils.isEmpty(salon.getSchedules())) {
            schedules = scheduleRepository.saveAll(salon.getSchedules());
        }

        User manager = userService.findByEmail(managerEmail);
        salon.setManager(manager);

        if (schedules != null) {
            salon.setSchedules(schedules);
        }

        salon.setActive(true);

        return salonRepository.save(salon);
    }

    public Salon findById(Long id) {
        return salonRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Salon> findAll(String salonOfferName) {
        if (salonOfferName == null || salonOfferName.isBlank()) {
            return (List<Salon>) salonRepository.findAll();
        } else {
            return salonRepository.findAllBySalonToSalonOffersSalonOfferNameAndIsActiveTrue(salonOfferName);
        }
    }

    public void deleteById(Long id) {
        Salon salon = findById(id);
        salon.setActive(false);
        salonRepository.save(salon);
    }

    public Salon update(Salon salon) {
        if (!salonRepository.existsByIdAndIsActiveTrue(salon.getId())) {
            throw new RuntimeException("Can not update salon.");
        }

        String managerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!salon.getManager().getEmail().equals(managerEmail)) {
            throw new RuntimeException("Can not update salon which does not belong to logged user.");
        }

        return save(salon, managerEmail);
    }


    public void assignEmployeeToSalon(Long salonId, Long employeeId) {
        Salon salon = findById(salonId);
        User user = userService.findById(employeeId);
        salon.getEmployees().add(user);
        this.salonRepository.save(salon);
    }

    public void removeEmployeeFromSalon(Long salonId, Long employeeId) {
        Salon salon = findById(salonId);
        User user = userService.findById(employeeId);
        salon.getEmployees().remove(user);
        this.salonRepository.save(salon);
    }

    public Salon findByManagerEmail(String managerEmail) {
        return salonRepository.findByManagerEmailAndIsActiveTrue(managerEmail)
                .orElseThrow(() -> new SalonNotFoundException("No salon found by manager email."));
    }


}
