package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Integer> {

    Optional<Salon> findByName(String name);

}
