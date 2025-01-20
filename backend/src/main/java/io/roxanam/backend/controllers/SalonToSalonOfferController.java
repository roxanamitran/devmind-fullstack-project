package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.SalonOfferDto;
import io.roxanam.backend.dtos.SalonToSalonOfferDto;
import io.roxanam.backend.entities.SalonOffer;
import io.roxanam.backend.entities.SalonToSalonOffer;
import io.roxanam.backend.mappers.SalonOfferMapper;
import io.roxanam.backend.mappers.SalonToSalonOfferMapper;
import io.roxanam.backend.repositories.SalonToSalonOfferRepository;
import io.roxanam.backend.services.SalonToSalonOfferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/salon-to-salon-offers")
@AllArgsConstructor
public class SalonToSalonOfferController {
    private SalonToSalonOfferService salonToSalonOfferService;
    private SalonToSalonOfferRepository salonToSalonOfferRepository;

    @PostMapping
    public SalonToSalonOfferDto save(@RequestBody SalonToSalonOfferDto dto) {
        return SalonToSalonOfferMapper.toDto(salonToSalonOfferService.save(SalonToSalonOfferMapper.toEntity(dto)));
    }

    @GetMapping
    public List<SalonToSalonOfferDto> findAllActive() {
        List<SalonToSalonOffer> salonToSalonOffers = salonToSalonOfferService.findAll();
         return salonToSalonOffers.stream().map(SalonToSalonOfferMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/salonOffers/{salonId}")
    public List<SalonToSalonOfferDto> findAlBySalon(@PathVariable("salonId") Long salonId) {
        List<SalonToSalonOffer> salonToSalonOffers = salonToSalonOfferService.findAllBySalon(salonId);

        return salonToSalonOffers.stream()
                .map(s -> SalonToSalonOfferMapper.toDto(s))
                .collect(Collectors.toList());
    }

    @GetMapping("/salons/{salonOfferId}")
    public List<SalonToSalonOfferDto> findAlBySalonOffer(@PathVariable("salonOfferId") Long salonOfferId) {
        List<SalonToSalonOffer> salonToSalonOffers = salonToSalonOfferService.findAllBySalonOffer(salonOfferId);

        return salonToSalonOffers.stream()
                .map(s -> SalonToSalonOfferMapper.toDto(s))
                .collect(Collectors.toList());
    }
}
