package com.quantitymeasurement.integrationTests;

import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.dto.QuantityInputDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuantityMeasurementIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddQuantitiesEndToEnd() {
        QuantityInputDTO input = new QuantityInputDTO();
        input.setThisQuantity(new QuantityDTO(2.0, "GALLON", "VolumeUnit"));
        input.setThatQuantity(new QuantityDTO(3.0, "LITRE", "VolumeUnit"));

        ResponseEntity<QuantityDTO> response = restTemplate.postForEntity(
                "/api/quantities/add",
                input,
                QuantityDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("LITRE", response.getBody().getUnit());
    }
}
