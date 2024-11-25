package io.roxanam.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.roxanam.backend.models.Department;
import io.roxanam.backend.models.Salon;
import io.roxanam.backend.services.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DepartmentDtoControllerTest {

    private static final Long DEPARTMENT_ID1 = 1L;
    private static final Long DEPARTMENT_ID2 = 2L;
    private static final String DEPARTMENT_NAME1 = "Massage";
    private static final String DEPARTMENT_NAME2 = "Nails";
    private static final String DEPARTMENT_PHOTO1 = "m.img";
    private static final String DEPARTMENT_PHOTO2 = "n.img";
    private static final Salon FIRST_SALON = new Salon();
    private static final Salon SECOND_SALON = new Salon();
    private static final List<Salon> SALON_LIST = new ArrayList<>(){{add(FIRST_SALON); add(SECOND_SALON);}};
    private static final Department DEPARTMENT_1 = new Department(DEPARTMENT_ID1, DEPARTMENT_NAME1, DEPARTMENT_PHOTO1, SALON_LIST );;
    private static final Department DEPARTMENT_2 = new Department(DEPARTMENT_ID2, DEPARTMENT_NAME2, DEPARTMENT_PHOTO2, SALON_LIST);
    private static final List<Department> DEPARTMENT = new ArrayList<>(){{
        add(DEPARTMENT_1);
        add(DEPARTMENT_2);
    }};
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    MockMvc mockMvc;
    @MockBean
    DepartmentService departmentService;

    @Test
    public void testGetAlldepartments() throws Exception {
        when(departmentService.findAll()).thenReturn(DEPARTMENT);
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(DEPARTMENT_ID1))
                .andExpect(jsonPath("$[0].name").value(DEPARTMENT_NAME1))
                .andExpect(jsonPath("$[0].photo").value(DEPARTMENT_PHOTO1))
                .andExpect(jsonPath("$[1].id").value(DEPARTMENT_ID2))
                .andExpect(jsonPath("$[1].name").value(DEPARTMENT_NAME2))
                .andExpect(jsonPath("$[1].photo").value(DEPARTMENT_PHOTO2));
    }

    @Test
    public void testSaveDepartment() throws Exception{
        when(departmentService.save(DEPARTMENT_1)).thenReturn(DEPARTMENT_1);

        mockMvc.perform(post("/departments")
                        .content(objectMapper.writeValueAsString(DEPARTMENT_1))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(DEPARTMENT_NAME1))
                .andExpect(jsonPath("$.photo").value(DEPARTMENT_PHOTO1));
    }

    @Test
    public void testGetDepartmentById() throws Exception{
        when(departmentService.findById(DEPARTMENT_ID1)).thenReturn(DEPARTMENT_1);
        mockMvc.perform(get("/departments/id/{id}", DEPARTMENT_ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DEPARTMENT_ID1))
                .andExpect(jsonPath("$.name").value(DEPARTMENT_NAME1))
                .andExpect(jsonPath("$.photo").value(DEPARTMENT_PHOTO1));
    }

    @Test
    public void testGetDepartmentByName() throws Exception{
        when(departmentService.findByName(DEPARTMENT_NAME2)).thenReturn(DEPARTMENT_2);
        mockMvc.perform(get("/departments/name/{name}", DEPARTMENT_NAME2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DEPARTMENT_ID2))
                .andExpect(jsonPath("$.name").value(DEPARTMENT_NAME2))
                .andExpect(jsonPath("$.photo").value(DEPARTMENT_PHOTO2));
    }

    @Test
    public void testDeleteDepartmentById() throws Exception {
        when(departmentService.deleteById(DEPARTMENT_ID1)).thenReturn("Department deleted.");
        mockMvc.perform(delete("/departments/id/{id}", DEPARTMENT_ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Department deleted."));
    }

    @Test
    public void testDeleteLoungeTypeByName() throws Exception {
        when(departmentService.deleteByName(DEPARTMENT_NAME1)).thenReturn("Lounge type deleted.");
        mockMvc.perform(delete("/departments/name/{name}", DEPARTMENT_NAME1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Department deleted."));
    }

    @Test
    public void testUpdateLoungeType() throws Exception {
        when(departmentService.update(DEPARTMENT_1)).thenReturn(DEPARTMENT_1);
        mockMvc.perform(put("/departments/{id}", DEPARTMENT_ID1)
                        .content(objectMapper.writeValueAsString(DEPARTMENT_1))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(DEPARTMENT_ID1))
                .andExpect(jsonPath("$[0].name").value(DEPARTMENT_NAME1))
                .andExpect(jsonPath("$[0].photo").value(DEPARTMENT_PHOTO1));
    }
}
