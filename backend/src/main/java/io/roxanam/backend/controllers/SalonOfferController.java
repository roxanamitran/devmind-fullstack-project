package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.SalonOffer;
import io.roxanam.backend.mappers.SalonOfferMapper;
import io.roxanam.backend.services.SalonOfferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/salonOffers")
public class SalonOfferController {

    private SalonOfferService salonOfferService;

    @PostMapping
    public SalonOffer save(@RequestBody SalonOffer salonOfferDto) {
        return SalonOfferMapper.toDto(salonOfferService.save(SalonOfferMapper.toEntity(salonOfferDto)));
    }

    @GetMapping
    public List<SalonOffer> findAll() {
        List<io.roxanam.backend.entities.SalonOffer> salonOffers = salonOfferService.findAll();
        return salonOffers.stream().map(SalonOfferMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SalonOffer findById(@PathVariable("id") Long id) {
        return SalonOfferMapper.toDto(salonOfferService.findById(id));
    }
}
