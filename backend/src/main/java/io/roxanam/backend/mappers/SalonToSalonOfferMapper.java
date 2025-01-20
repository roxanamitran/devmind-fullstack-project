package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.SalonToSalonOfferDto;
import io.roxanam.backend.entities.SalonToSalonOffer;

public class SalonToSalonOfferMapper {
    public static SalonToSalonOffer toEntity(SalonToSalonOfferDto dto) {
        if (dto == null) {
            return null;
        }

        SalonToSalonOffer entity = new SalonToSalonOffer();
        entity.setId(dto.getId());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        entity.setSalon(SalonMapper.toEntity(dto.getSalon()));
        entity.setSalonOffer(SalonOfferMapper.toEntity(dto.getSalonOffer()));

        return entity;
    }

    public static SalonToSalonOfferDto toDto(SalonToSalonOffer entity) {
        if(entity == null) {
            return null;
        }

        SalonToSalonOfferDto dto = new SalonToSalonOfferDto();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setSalon(SalonMapper.toDto(entity.getSalon()));
        dto.setSalonOffer(SalonOfferMapper.toDto(entity.getSalonOffer()));

        return dto;
    }
}
