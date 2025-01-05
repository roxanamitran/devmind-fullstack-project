package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.Appointment;
import io.roxanam.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAllByClient_Id(Integer clientId);
    List<Appointment> findAllByEmployee_Id(Integer employeeId);
}
