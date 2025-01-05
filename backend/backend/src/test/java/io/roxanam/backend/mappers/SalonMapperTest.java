package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.SalonDto;
import io.roxanam.backend.entities.Salon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalonMapperTest {

    private static final Integer ID = 1;
    private static final String NAME = "Beauty Concept";
    private static final String ADDRESS = "Aleea Teilor nr 4";
    private static final String EMAIL = "beautyconceptQyahoo.com";
    private static final String PHONE = "0766543421";
    private static final String PHOTO = "i.img";
    private static final List<SalonDto> DEPARTMENTS_DTO = new ArrayList<>();
    private static final List<SalonDto> DEPARTMENTS = new ArrayList<>();


//    @Test
//    void toEntity() {
//        SalonDto salonDto = new SalonDto(ID, NAME, ADDRESS, EMAIL, PHONE, PHOTO, DEPARTMENTS_DTO);
//
//        Salon salon = SalonMapper.toEntity(salonDto);
//
//        assertEquals(ID, salon.getId());
//        assertEquals(NAME, salon.getName());
//        assertEquals(ADDRESS, salon.getAddress());
//        assertEquals(EMAIL, salon.getEmail());
//        assertEquals(PHONE, salon.getPhone());
//        assertEquals(PHOTO, salon.getPhoto());
//        assertEquals(DEPARTMENTS, salon.get());
//    }
//
//    @Test
//    void toDto() {
//        Salon salon = new Salon(ID, NAME, ADDRESS, EMAIL, PHONE, PHOTO, DEPARTMENTS);
//
//        SalonDto salonDto = SalonMapper.toDto(salon);
//
//        assertEquals(ID, salonDto.getId());
//        assertEquals(NAME, salonDto.getName());
//        assertEquals(ADDRESS, salonDto.getAddress());
//        assertEquals(EMAIL, salonDto.getEmail());
//        assertEquals(PHONE, salonDto.getPhone());
//        assertEquals(PHOTO, salonDto.getPhoto());
//        assertEquals(DEPARTMENTS, salonDto.getDepartments());
//    }
//}
