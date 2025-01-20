package io.roxanam.backend.dtos;

import lombok.Data;

@Data
public class SalonToSalonOfferDto {
    private Long id;
    private SalonDto salon;
    private SalonOfferDto salonOffer;
    private Double price;
    private Integer duration;
}
