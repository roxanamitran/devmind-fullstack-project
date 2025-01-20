package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.Salon;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonRepository extends CrudRepository<Salon, Long>, QuerydslPredicateExecutor<Salon> {
    boolean existsByIdAndIsActiveTrue(Long id);
    List<Salon> findAllByIsActiveTrue();
    Salon findByNameAndIsActiveTrue(String name);
    List<Salon> findAllByAddressAndIsActiveTrue(String address);
}
