package io.roxanam.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.roxanam.backend.models.LoungeType;
import io.roxanam.backend.services.LoungeTypeService;
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
public class LoungeTypeControllerTest {

    private static final Long LOUNGE_TYPE_ID1 = 1L;
    private static final Long LOUNGE_TYPE_ID2 = 2L;
    private static final String LOUNGE_TYPE_NAME1 = "Massage";
    private static final String LOUNGE_TYPE_NAME2 = "Nails";
    private static final String LOUNGE_TYPE_PHOTO1 = "m.img";
    private static final String LOUNGE_TYPE_PHOTO2 = "n.img";
    private static final LoungeType LOUNGE_TYPE_1 = new LoungeType(LOUNGE_TYPE_ID1, LOUNGE_TYPE_NAME1, LOUNGE_TYPE_PHOTO1);;
    private static final LoungeType LOUNGE_TYPE_2 = new LoungeType(LOUNGE_TYPE_ID2, LOUNGE_TYPE_NAME2, LOUNGE_TYPE_PHOTO2);
    private static final List<LoungeType> LOUNGE_TYPES = new ArrayList<>(){{
        add(LOUNGE_TYPE_1);
        add(LOUNGE_TYPE_2);
    }};
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;
    @MockBean
    LoungeTypeService loungeTypeService;

    @Test
    public void testGetAllLoungeTypes() throws Exception {
        when(loungeTypeService.findAll()).thenReturn(LOUNGE_TYPES);
        mockMvc.perform(get("/lounge-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(LOUNGE_TYPE_ID1))
                .andExpect(jsonPath("$[0].name").value(LOUNGE_TYPE_NAME1))
                .andExpect(jsonPath("$[0].photo").value(LOUNGE_TYPE_PHOTO1))
                .andExpect(jsonPath("$[1].id").value(LOUNGE_TYPE_ID2))
                .andExpect(jsonPath("$[1].name").value(LOUNGE_TYPE_NAME2))
                .andExpect(jsonPath("$[1].photo").value(LOUNGE_TYPE_PHOTO2));
    }

    @Test
    public void testSaveLoungeType() throws Exception{
        when(loungeTypeService.save(LOUNGE_TYPE_1)).thenReturn(LOUNGE_TYPE_1);

        mockMvc.perform(post("/lounge-types")
                        .content(objectMapper.writeValueAsString(LOUNGE_TYPE_1))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(LOUNGE_TYPE_NAME1))
                .andExpect(jsonPath("$.photo").value(LOUNGE_TYPE_PHOTO1));
    }

    @Test
    public void testGetLoungeById() throws Exception{
        when(loungeTypeService.findById(LOUNGE_TYPE_ID1)).thenReturn(LOUNGE_TYPE_1);
        mockMvc.perform(get("/lounge-types/id/{id}", LOUNGE_TYPE_ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(LOUNGE_TYPE_ID1))
                .andExpect(jsonPath("$.name").value(LOUNGE_TYPE_NAME1))
                .andExpect(jsonPath("$.photo").value(LOUNGE_TYPE_PHOTO1));
    }

    @Test
    public void testGetLoungeTypeByName() throws Exception{
        when(loungeTypeService.findByName(LOUNGE_TYPE_NAME2)).thenReturn(LOUNGE_TYPE_2);
        mockMvc.perform(get("/lounge-types/name/{name}", LOUNGE_TYPE_NAME2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(LOUNGE_TYPE_ID2))
                .andExpect(jsonPath("$.name").value(LOUNGE_TYPE_NAME2))
                .andExpect(jsonPath("$.photo").value(LOUNGE_TYPE_PHOTO2));
    }

    @Test
    public void testDeleteLoungeTypeById() throws Exception {
        when(loungeTypeService.deleteById(LOUNGE_TYPE_ID1)).thenReturn("Lounge type deleted.");
        mockMvc.perform(delete("/lounge-types/id/{id}", LOUNGE_TYPE_ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Lounge type deleted."));
    }

    @Test
    public void testDeleteLoungeTypeByName() throws Exception {
        when(loungeTypeService.deleteByName(LOUNGE_TYPE_NAME1)).thenReturn("Lounge type deleted.");
        mockMvc.perform(delete("/lounge-types/name/{name}", LOUNGE_TYPE_NAME1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Lounge type deleted."));
    }

    @Test
    public void testUpdateLoungeType() throws Exception {
        when(loungeTypeService.updateById(LOUNGE_TYPE_ID1, LOUNGE_TYPE_1)).thenReturn("LoungeType updated successfully");
        mockMvc.perform(put("/lounge-types/{id}", LOUNGE_TYPE_ID1)
                        .content(objectMapper.writeValueAsString(LOUNGE_TYPE_1))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("LoungeType updated successfully"));
    }
}
