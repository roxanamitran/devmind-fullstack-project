package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.Appointment;
import io.roxanam.backend.entities.AppointmentStatus;
import io.roxanam.backend.entities.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByEmployeeId(Long id);
    List<Appointment> findAllByCustomerId(Long id);
    List<Appointment> findAllBySalon(Salon salon);
    List<Appointment> findAllByEmployeeIdAndStartDateBetweenAndStatus(Long id, Instant start, Instant end, AppointmentStatus status);
    List<Appointment> findAllByEmployeeEmail(String email);
    List<Appointment> findAllByCustomerEmail(String email);
    List<Appointment> findAllBySalonManagerEmail(String email);
}