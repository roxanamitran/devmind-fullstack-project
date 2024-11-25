package io.roxanam.backend.services;


import io.roxanam.backend.exceptions.DepartmentFindException;
import io.roxanam.backend.models.Department;
import io.roxanam.backend.models.Salon;
import io.roxanam.backend.repositories.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentDtoServiceTest {

    private static final Long DEPARTMENT_ID = 1L;
    private static final String DEPARTMENT_NAME = "massage";
    private static final String DEPARTMENT_PHOTO = "m.img";
    private static final Salon FIRST_SALON = new Salon();
    private static final Salon SECOND_SALON = new Salon();

    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private DepartmentService departmentService;

    private Department department = new Department(DEPARTMENT_ID, DEPARTMENT_NAME, DEPARTMENT_PHOTO, Collections.emptyList());;


    @Test
    void testSaveSuccess() {
        // given
        when(departmentRepository.save(department)).thenReturn(department);

        // when
        Department savedDepartment = departmentService.save(department);

        // then
        assertThat(savedDepartment).isEqualTo(department);
    }

    @Test
    void testFindByIdSuccess() {
        // given
        when(departmentRepository.findById(DEPARTMENT_ID))
                .thenReturn(Optional.of(department));


        // when
        Department foundDepartment = departmentService.findById(DEPARTMENT_ID);

        // then
        assertThat(foundDepartment)
                .isEqualTo(department);
    }

    @Test
    void testFindByIdFail() {
        // given
        when(departmentRepository.findById(DEPARTMENT_ID))
                .thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> departmentService.findById(DEPARTMENT_ID))
                .isInstanceOf(DepartmentFindException.class)
                .hasMessage("Can not find department with id: " + DEPARTMENT_ID);
    }

    @Test
    public void testDeleteById() {
        // given
        doNothing().when(departmentRepository).deleteById(DEPARTMENT_ID);

        // when
        String response = departmentService.deleteById(DEPARTMENT_ID);

        // then
        assertThat(response).isEqualTo("Department deleted successfully");
    }

   @Test
    public void testFindAll() {
        // given
       List<Department> departments = List.of(department);
       when(departmentRepository.findAll()).thenReturn(departments);

       // when
       List<Department> all = departmentService.findAll();

        // then
       assertThat(all).isEqualTo(departments);
   }
}
