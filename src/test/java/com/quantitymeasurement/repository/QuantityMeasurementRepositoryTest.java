package com.quantitymeasurement.repository;

import com.quantitymeasurement.model.OperationType;
import com.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class QuantityMeasurementRepositoryTest {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Test
    public void testSaveAndRetrieveEntity() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setThisValue(10.0);
        entity.setThisUnit("LITRE");
        entity.setThisMeasurementType("VolumeUnit");
        entity.setOperation(OperationType.ADD);
        entity.setError(false);
        entity.setResultString("10 LITRE");

        QuantityMeasurementEntity savedEntity = repository.save(entity);
        assertNotNull(savedEntity.getId());

        List<QuantityMeasurementEntity> results = repository.findAll();
        assertEquals(1, results.size());
        assertEquals(10.0, results.get(0).getThisValue());
    }
}
