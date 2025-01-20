package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.SalonOfferDto;
import io.roxanam.backend.entities.SalonOffer;

public class SalonOfferMapper {

    public static SalonOffer toEntity(SalonOfferDto dto) {
        if (dto == null) {
            return null;
        }

        SalonOffer entity = new SalonOffer();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPhotoUrl(dto.getPhotoUrl());

        return entity;
    }

    public static SalonOfferDto toDto(SalonOffer entity) {
        if (entity == null) {
            return  null;
        }

        SalonOfferDto dto = new SalonOfferDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhotoUrl(entity.getPhotoUrl());

        return dto;
    }
}
