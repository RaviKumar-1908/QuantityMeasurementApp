package com.quantitymeasurement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.dto.QuantityInputDTO;
import com.quantitymeasurement.service.IQuantityMeasurementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuantityMeasurementController.class)
public class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuantityMeasurementService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCompareEndpoint() throws Exception {
        QuantityInputDTO input = new QuantityInputDTO();
        input.setThisQuantity(new QuantityDTO(1.0, "FEET", "LengthUnit"));
        input.setThatQuantity(new QuantityDTO(12.0, "INCHES", "LengthUnit"));

        when(service.compare(any(), any())).thenReturn(true);

        mockMvc.perform(post("/api/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testAddEndpoint() throws Exception {
        QuantityInputDTO input = new QuantityInputDTO();
        input.setThisQuantity(new QuantityDTO(1.0, "FEET", "LengthUnit"));
        input.setThatQuantity(new QuantityDTO(2.0, "INCHES", "LengthUnit"));

        QuantityDTO mockResult = new QuantityDTO(14.0, "INCHES", "LengthUnit");
        when(service.add(any(), any())).thenReturn(mockResult);

        mockMvc.perform(post("/api/quantities/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(14.0))
                .andExpect(jsonPath("$.unit").value("INCHES"));
    }
}
