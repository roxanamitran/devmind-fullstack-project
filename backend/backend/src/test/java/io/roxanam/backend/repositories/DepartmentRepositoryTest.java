package io.roxanam.backend.repositories;

import io.roxanam.backend.models.Department;
import io.roxanam.backend.models.Salon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class DepartmentDtoRepositoryTest {

    private static final Long DEPARTMENT_ID = 1L;
    private static final String DEPARTMENT_NAME = "Massage";
    private static final String DEPARTMENT_PHOTO = "m.img";
    private static final Salon FIRST_SALON = new Salon();
    private static final Salon SEcond_SALON = new Salon();
    private static final List<Salon> SALON_LIST = new ArrayList<>(){{add(FIRST_SALON); add(SEcond_SALON);}};
    private static final Department DEPARTMENT = new Department(DEPARTMENT_ID, DEPARTMENT_NAME, DEPARTMENT_PHOTO, SALON_LIST);

    @Autowired
    private DepartmentRepository loungeTypeRepository;


    @Test
    public void testFindByNameWhenPresent() {
        loungeTypeRepository.save(DEPARTMENT);

        Optional<Department> optionalLoungeType = loungeTypeRepository.findByName(DEPARTMENT_NAME);

        assertTrue(optionalLoungeType.isPresent());

        Department department = optionalLoungeType.get();
        assertEquals(DEPARTMENT_ID, department.getId());
        assertEquals(DEPARTMENT_NAME, department.getName());
        assertEquals(DEPARTMENT_PHOTO, department.getPhoto());
    }

    @Test
    public void testFindByNameWhenDBEmpty() {
        Optional<Department> optionalLoungeType = loungeTypeRepository.findByName(DEPARTMENT_NAME);

        assertTrue(optionalLoungeType.isEmpty());
    }

    @Test
    public void testDeleteByNameWhenPresent() {
        loungeTypeRepository.save(DEPARTMENT);
        loungeTypeRepository.deleteByName(DEPARTMENT_NAME);
        assertTrue(loungeTypeRepository.findByName(DEPARTMENT_NAME).isEmpty());
    }

    @Test
    public void testDeleteByNameWhenNotPresent() {
        loungeTypeRepository.save(DEPARTMENT);
        loungeTypeRepository.deleteByName("TEST");
        assertTrue(loungeTypeRepository.findAll().size() == 1);
    }






}
