package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {
    List<ServiceType> findAllBySalon_Id(Integer salonId);
}
