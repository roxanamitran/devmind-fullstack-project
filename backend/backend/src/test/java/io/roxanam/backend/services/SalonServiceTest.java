package io.roxanam.backend.services;

import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.repositories.SalonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalonServiceTest {

    @Mock
    private SalonRepository salonRepository;

    @InjectMocks
    private SalonService salonService;


    private List<Salon> salonList = new ArrayList<>(){{
        add(salon);
    }};

    private static final Integer SALON_ID = 1;
    private static final String SALON_NAME = "Beauty Concept";
    private static final String SALON_ADDRESS = "Aleea Teilor Nr.12";
    private static final String SALON_EMAIL = "Aleea Teilor Nr.12";
    private static final String SALON_PHONE = "021657876";
    private static final String SALON_PHOTO = "p.img";
    private static final List<Department> LIST_OF_DEPARTMENTS = new ArrayList<>(){{
        add(new Department(1L, "HairStyling", "h.img"));
    }};

    private Salon salon = new Salon(SALON_ID, SALON_NAME, SALON_ADDRESS,
            SALON_EMAIL, SALON_PHONE, SALON_PHOTO, LIST_OF_DEPARTMENTS);

    @Test
    void testFindAll() {
        when(salonRepository.findAll()).thenReturn(salonList);
        assertThat(salonService.findAll().size() == salonList.size());
    }

    @Test
    void findById(){
        when(salonRepository.findById(SALON_ID)).thenReturn(Optional.of(salon));
        assertThat(salonService.findById(SALON_ID)).isEqualTo(salon);
    }

    @Test
    void save(){
        when(salonRepository.save(salon)).thenReturn(salon);
        assertThat(salonService.save(salon)).isEqualTo(salon);
    }

    @Test
    void deleteById() {
       doNothing().when(salonRepository).deleteById(SALON_ID);
       assertThat(salonService.deleteById(SALON_ID)).isEqualTo("Salon deleted successfully");
    }
}
