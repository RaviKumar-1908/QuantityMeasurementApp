package com.quantitymeasurement.entity;

import com.quantitymeasurement.model.OperationType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementEntityTest {

    @Test
    public void testEntityGettersAndSetters() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setId(1L);
        entity.setThisValue(12.0);
        entity.setThisUnit("INCHES");
        entity.setThisMeasurementType("LengthUnit");
        entity.setResultString("Equal");
        entity.setError(false);
        entity.setOperation(OperationType.COMPARE);

        assertEquals(1L, entity.getId());
        assertEquals(12.0, entity.getThisValue());
        assertEquals("INCHES", entity.getThisUnit());
        assertEquals("LengthUnit", entity.getThisMeasurementType());
        assertEquals("Equal", entity.getResultString());
        assertFalse(entity.isError());
        assertEquals(OperationType.COMPARE, entity.getOperation());
    }
}
