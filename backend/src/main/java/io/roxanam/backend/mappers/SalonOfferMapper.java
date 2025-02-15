package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.SalonOffer;

public class SalonOfferMapper {

    public static io.roxanam.backend.entities.SalonOffer toEntity(SalonOffer dto) {
        if (dto == null) {
            return null;
        }

        io.roxanam.backend.entities.SalonOffer entity = new io.roxanam.backend.entities.SalonOffer();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPhotoUrl(dto.getPhotoUrl());

        return entity;
    }

    public static SalonOffer toDto(io.roxanam.backend.entities.SalonOffer entity) {
        if (entity == null) {
            return  null;
        }

        SalonOffer dto = new SalonOffer();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhotoUrl(entity.getPhotoUrl());

        return dto;
    }
}
