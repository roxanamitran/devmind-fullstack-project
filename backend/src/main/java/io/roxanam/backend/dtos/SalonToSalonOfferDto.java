package io.roxanam.backend.dtos;

import lombok.Data;

@Data
public class SalonToSalonOfferDto {
    private Long id;
    private SalonDto salon;
    private SalonOffer salonOffer;
    private Double price;
    private Integer duration;
}
