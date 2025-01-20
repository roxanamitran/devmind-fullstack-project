package io.roxanam.backend.services;

import ch.qos.logback.core.util.StringUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import io.roxanam.backend.entities.QSalon;
import io.roxanam.backend.entities.QSalonToSalonOffer;
import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.entities.Schedule;
import io.roxanam.backend.repositories.SalonRepository;
import io.roxanam.backend.repositories.SalonToSalonOfferRepository;
import io.roxanam.backend.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
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

    public Salon save(Salon salon) {
        List<Schedule> schedules = null;
        if (!CollectionUtils.isEmpty(salon.getSchedules())) {
            schedules = scheduleRepository.saveAll(salon.getSchedules());
        }

        if (salon.getManager() != null) {
            salon.setManager(userService.findById(salon.getManager().getId()));
        }

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

        if (salonName != null && salonName.trim().length() > 0) {
            query = query.and(salon.name.containsIgnoreCase(salonName));
        }

        if (salonAddress != null && salonAddress.trim().length() > 0) {
            query = query.and(salon.address.containsIgnoreCase(salonAddress));
        }

        if (salonOfferName != null && salonOfferName.trim().length() > 0) {
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

        return save(salon);
    }

    public Salon findByName(String name) {
        return salonRepository.findByNameAndIsActiveTrue(name);
    }

    public List<Salon> findAllByAddress(String address) {
        return salonRepository.findAllByAddressAndIsActiveTrue(address);
    }
}
