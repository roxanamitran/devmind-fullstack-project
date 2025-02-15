package io.roxanam.backend.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import io.roxanam.backend.entities.*;
import io.roxanam.backend.repositories.SalonRepository;
import io.roxanam.backend.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public List<Salon> findAll(String salonName, String salonAddress, String salonOfferName) {
        QSalon salon = QSalon.salon;
        QSalonToSalonOffer salonToSalonOffer = QSalonToSalonOffer.salonToSalonOffer;

        BooleanExpression query = salon.isActive.eq(true);

        if (salonName != null && !salonName.trim().isEmpty()) {
            query = query.and(salon.name.containsIgnoreCase(salonName));
        }

        if (salonAddress != null && !salonAddress.trim().isEmpty()) {
            query = query.and(salon.address.containsIgnoreCase(salonAddress));
        }

        if (salonOfferName != null && !salonOfferName.trim().isEmpty()) {
            query = query.and(
                    JPAExpressions.selectOne()
                            .from(salonToSalonOffer)
                            .where(salonToSalonOffer.salon.eq(salon)
                                    .and(salonToSalonOffer.salonOffer.name.eq(salonOfferName)))
                            .exists());
        }

        return StreamSupport.stream(salonRepository.findAll(query).spliterator(), false)
                .collect(Collectors.toList());
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

    public Salon findByName(String name) {
        return salonRepository.findByNameAndIsActiveTrue(name).orElseThrow(() -> new RuntimeException("No salon found by name."));
    }

    public Salon findByManagerEmail(String managerEmail) {
        return salonRepository.findByManagerEmailAndIsActiveTrue(managerEmail).orElseThrow(() -> new RuntimeException("No salon found by manager email."));
    }

    public List<Salon> findAllByAddress(String address) {
        return salonRepository.findAllByAddressAndIsActiveTrue(address);
    }
}
