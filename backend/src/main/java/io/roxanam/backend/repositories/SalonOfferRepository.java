package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.SalonOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonOfferRepository  extends JpaRepository<SalonOffer, Long> {

    List<SalonOffer> findAllByIsActiveTrue();
    SalonOffer findSalonOfferByName(String name);

}
