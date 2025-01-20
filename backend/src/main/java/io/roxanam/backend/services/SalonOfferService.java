package io.roxanam.backend.services;

import io.roxanam.backend.entities.SalonOffer;
import io.roxanam.backend.repositories.SalonOfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SalonOfferService {
    private SalonOfferRepository salonOfferRepository;

    public SalonOffer save(SalonOffer salonOffer) {
         return salonOfferRepository.save(salonOffer);
    }

    public SalonOffer findById(Long id) {
        return salonOfferRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<SalonOffer> findAll() {
        return salonOfferRepository.findAllByIsActiveTrue();
    }
}
