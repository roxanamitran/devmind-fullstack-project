package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.Salon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class SalonRepositoryTest {
    private static final String SALON_NAME = "Beauty Concept";
    private static final String SALON_ADDRESS = "Aleea Teilor Nr.12";
    private static final String SALON_EMAIL = "Aleea Teilor Nr.12";
    private static final String SALON_PHONE = "021657876";
    private static final String SALON_PHOTO = "p.img";
    private static final String DEPARTMENT_NAME = "HairStyling";
    private static final String DEPARTMENT_PHOTO = "h.img";

    @Autowired
    SalonRepository salonRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    void findByName() {
        Department department = new Department(null, DEPARTMENT_NAME, DEPARTMENT_PHOTO);
        department = departmentRepository.save(department);

        Salon salon = new Salon(null, SALON_NAME, SALON_ADDRESS,
                SALON_EMAIL, SALON_PHONE, SALON_PHOTO, List.of(department));
        salonRepository.save(salon);

        Optional<Salon> optionalSalon = salonRepository.findByName(SALON_NAME);
        assertThat(optionalSalon).isPresent();
        Salon foundSalon = optionalSalon.get();

        assertEquals(SALON_NAME, foundSalon.getName());
        assertEquals(SALON_ADDRESS, foundSalon.getAddress());
        assertNotNull(foundSalon.getId());
        assertEquals(SALON_PHONE, foundSalon.getPhone());
        assertEquals(SALON_PHOTO, foundSalon.getPhoto());
        assertEquals(SALON_EMAIL, foundSalon.getEmail());
        assertEquals(1, foundSalon.getDepartments().size());

        Department foundDepartment = foundSalon.getDepartments().get(0);
        assertEquals(DEPARTMENT_NAME, foundDepartment.getName());
        assertEquals(DEPARTMENT_PHOTO, foundDepartment.getPhoto());
    }
}
