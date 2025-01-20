package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.Appointment;
import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByIdAndIsActiveTrue(Long id);
    List<Appointment> findAllByEmployeeAndIsActiveTrue(User employee);
    List<Appointment> findAllByCustomerAndIsActiveTrue(User customer);
    List<Appointment> findAllBySalonAndIsActiveTrue(Salon salon);
}
