package com.quantitymeasurement.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityDTOTest {

    @Test
    public void testQuantityDTOCreationAndRetrieval() {
        QuantityDTO dto = new QuantityDTO(5.0, "FEET", "LengthUnit");
        
        assertEquals(5.0, dto.getValue());
        assertEquals("FEET", dto.getUnit());
        assertEquals("LengthUnit", dto.getMeasurementType());
    }
    
    @Test
    public void testQuantityDTOStringOutput() {
        QuantityDTO dto = new QuantityDTO(5.0, "FEET", "LengthUnit");
        assertEquals("5 FEET", dto.toString());
    }
}
