package com.quantitymeasurement.unit;

import com.quantitymeasurement.dto.QuantityDTO.LengthUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LengthUnitTest {

    @Test
    public void testLengthUnitEnumsExist() {
        assertNotNull(LengthUnit.FEET);
        assertNotNull(LengthUnit.INCHES);
        assertNotNull(LengthUnit.YARDS);
        assertNotNull(LengthUnit.CENTIMETERS);
    }
    
    @Test
    public void testMeasurementTypeReturnsCorrectly() {
        assertEquals("LengthUnit", LengthUnit.FEET.getMeasurementType());
        assertEquals("FEET", LengthUnit.FEET.getUnitName());
    }
}
