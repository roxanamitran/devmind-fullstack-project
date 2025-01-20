package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.SalonToSalonOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonToSalonOfferRepository extends JpaRepository<SalonToSalonOffer, Long> {
    List<SalonToSalonOffer> findAllBySalonIsActiveTrue();
    List<SalonToSalonOffer> findAllBySalonIdAndSalonIsActiveTrue(Long id);
    List<SalonToSalonOffer> findAllBySalonOfferIdAndIsActiveTrue(Long id);
}
