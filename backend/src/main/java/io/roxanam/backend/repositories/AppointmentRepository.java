package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.Appointment;
import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByEmployeeId(Long id);
    List<Appointment> findAllByCustomerId(Long id);
    List<Appointment> findAllBySalon(Salon salon);
    List<Appointment> findAllByEmployeeIdAndStartDateBetween(Long id, Instant start, Instant end);
    List<Appointment> findAllByEmployeeEmail(String email);
    List<Appointment> findAllByCustomerEmail(String email);
    List<Appointment> findAllBySalonManagerEmail(String email);
}