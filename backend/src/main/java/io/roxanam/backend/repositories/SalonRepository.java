package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.Salon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalonRepository extends CrudRepository<Salon, Long> {
    boolean existsByIdAndIsActiveTrue(Long id);
    Optional<Salon> findByManagerEmailAndIsActiveTrue(String managerEmail);
    List<Salon> findAllBySalonToSalonOffersSalonOfferNameAndIsActiveTrue(@Param("name") String name);
}
