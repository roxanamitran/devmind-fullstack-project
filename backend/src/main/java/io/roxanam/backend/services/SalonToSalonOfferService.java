package io.roxanam.backend.services;

import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.entities.SalonOffer;
import io.roxanam.backend.entities.SalonToSalonOffer;
import io.roxanam.backend.repositories.SalonToSalonOfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SalonToSalonOfferService {
    private SalonToSalonOfferRepository salonToSalonOfferRepository;
    private SalonOfferService salonOfferService;
    private SalonService salonService;

    public SalonToSalonOffer save(SalonToSalonOffer salonToSalonOffer) {
        salonToSalonOffer.setSalonOffer(salonOfferService.findById(salonToSalonOffer.getSalonOffer().getId()));
        salonToSalonOffer.setSalon(salonService.findById(salonToSalonOffer.getSalon().getId()));

        return salonToSalonOfferRepository.save(salonToSalonOffer);
    }

    public List<SalonToSalonOffer> findAll() {
        return salonToSalonOfferRepository.findAll();
    }

    public SalonToSalonOffer findById(Long id) {
        return salonToSalonOfferRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found."));
    }

    public List<SalonToSalonOffer> findAllBySalon(Long salonId) {
        return salonToSalonOfferRepository.findAllBySalonIdAndSalonIsActiveTrue(salonId);
    }

    public List<SalonToSalonOffer> findAllBySalonOffer(Long salonOfferId) {
        return salonToSalonOfferRepository.findAllBySalonOfferIdAndIsActiveTrue(salonOfferId);
    }


    public void delete(Long id) {
        salonToSalonOfferRepository.deleteById(id);
    }
}
