package io.roxanam.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.roxanam.backend.entities.Salon;
import io.roxanam.backend.services.SalonService;
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

@WebMvcTest(controllers = SalonController.class)
public class SalonControllerTest { ;
    private static final Integer SALON_ID = 1;
    private static final String SALON_NAME = "Beauty Concept";
    private static final String SALON_ADDRESS = "Aleea Teiolor nr 5";
    private static final String SALON_EMAIL = "beautyconcept@Yahoo.com";
    private static final String SALON_PHONE = " 07654329876";
    private static final String SALON_PHOTO = "s.img";
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final Long ID_DEPARTMENT_1 = 1L;
    private static final Long ID_DEPARTMENT_2 = 2L;
    private static final String NAME_DEPARTMENT_1 = "Nails";
    private static final String NAME_DEPARTMENT_2 = "Hairstyling";
    private static final String PHOTO_DEPARTMENT_1 = "n.img";
    private static final String PHOTO_DEPARTMENT_2 = "h.img";
    private static final Department DEPARTMENT_1 = new Department(ID_DEPARTMENT_1, NAME_DEPARTMENT_1, PHOTO_DEPARTMENT_1);
    private static final Department DEPARTMENT_2 = new Department(ID_DEPARTMENT_2, NAME_DEPARTMENT_2, PHOTO_DEPARTMENT_2);
    private static final List<Department> DEPARTMENT_LIST = new ArrayList<>(){{add(DEPARTMENT_1);
    add(DEPARTMENT_2);}};
    private static final Salon SALON = new Salon(SALON_ID, SALON_NAME, SALON_ADDRESS,
            SALON_EMAIL, SALON_PHONE, SALON_PHOTO, DEPARTMENT_LIST);
    private static final List<Salon> SALON_LIST = new ArrayList<>(){{add(SALON);}};

    @Autowired
    MockMvc mockMvc;
    @MockBean
    SalonService salonService;

    @Test
    void testSave() throws Exception{
        when(salonService.save(SALON)).thenReturn(SALON);
        mockMvc.perform(post("/salons")
                .content(objectMapper.writeValueAsString(SALON))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(SALON_NAME))
                .andExpect(jsonPath("$.email").value(SALON_EMAIL))
                .andExpect(jsonPath("$.phone").value(SALON_PHONE))
                .andExpect(jsonPath("$.id").value(SALON_ID))
                .andExpect(jsonPath("$.address").value(SALON_ADDRESS))
                .andExpect(jsonPath("$.departments.size()").value(2))
                .andExpect(jsonPath("$.departments[0].name").value(NAME_DEPARTMENT_1))
                .andExpect(jsonPath("$.departments[0].photo").value(PHOTO_DEPARTMENT_1))
                .andExpect(jsonPath("$.departments[0].id").value(ID_DEPARTMENT_1))
                .andExpect(jsonPath("$.departments[1].name").value(NAME_DEPARTMENT_2))
                .andExpect(jsonPath("$.departments[1].photo").value(PHOTO_DEPARTMENT_2))
                .andExpect(jsonPath("$.departments[1].id").value(ID_DEPARTMENT_2));
    }

    @Test
    void testGetAll() throws Exception{
        when(salonService.findAll()).thenReturn(SALON_LIST);
        mockMvc.perform(get("/salons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(SALON_NAME))
                .andExpect(jsonPath("$[0].email").value(SALON_EMAIL))
                .andExpect(jsonPath("$[0].phone").value(SALON_PHONE))
                .andExpect(jsonPath("$[0].id").value(SALON_ID))
                .andExpect(jsonPath("$[0].address").value(SALON_ADDRESS))
                .andExpect(jsonPath("$[0].departments.size()").value(2));

    }

    @Test
    void testGetById() throws Exception {
        when(salonService.findById(SALON_ID)).thenReturn(SALON);
        mockMvc.perform(get("/salons/{id}", SALON_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(SALON_NAME))
                .andExpect(jsonPath("$.email").value(SALON_EMAIL))
                .andExpect(jsonPath("$.phone").value(SALON_PHONE))
                .andExpect(jsonPath("$.id").value(SALON_ID))
                .andExpect(jsonPath("$.address").value(SALON_ADDRESS))
                .andExpect(jsonPath("$.departments.size()").value(2));
    }

    @Test
    void testDeleteById() throws Exception{
        when(salonService.deleteById(SALON_ID)).thenReturn("Salon deleted successfully");
        mockMvc.perform(delete("/salons/{id}", SALON_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Salon deleted successfully"));
    }

    @Test
    void testUpdate() throws Exception{
        when(salonService.save(SALON)).thenReturn(SALON);
        mockMvc.perform(put("/salons/{id}", SALON_ID)
                .content(objectMapper.writeValueAsString(SALON))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(SALON_NAME))
                .andExpect(jsonPath("$.email").value(SALON_EMAIL))
                .andExpect(jsonPath("$.phone").value(SALON_PHONE))
                .andExpect(jsonPath("$.id").value(SALON_ID))
                .andExpect(jsonPath("$.address").value(SALON_ADDRESS))
                .andExpect(jsonPath("$.departments.size()").value(2));
    }
}
