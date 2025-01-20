package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.Holiday;
import io.roxanam.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findAllByEmployeeAndIsActiveTrue(User employee);
}
