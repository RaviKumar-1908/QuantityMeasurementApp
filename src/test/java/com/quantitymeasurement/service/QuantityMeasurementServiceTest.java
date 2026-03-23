package com.quantitymeasurement.service;

import com.quantitymeasurement.model.OperationType;
import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuantityMeasurementServiceTest {

    @Mock
    private QuantityMeasurementRepository repository;

    @InjectMocks
    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCompareQuantitiesEqual() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

        when(repository.save(any(QuantityMeasurementEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        boolean result = service.compare(q1, q2);
        assertTrue(result);
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    public void testCompareQuantitiesNotEqual() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(1.0, "INCHES", "LengthUnit");

        when(repository.save(any(QuantityMeasurementEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        boolean result = service.compare(q1, q2);
        assertFalse(result);
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    public void testAddQuantities() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(2.0, "INCHES", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");

        when(repository.save(any(QuantityMeasurementEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        QuantityDTO result = service.add(q1, q2, target);
        assertEquals(14.0, result.getValue());
        assertEquals("INCHES", result.getUnit());
    }

    @Test
    public void testAddDifferentCategoriesThrowsException() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(1.0, "GALLON", "VolumeUnit");

        assertThrows(IllegalArgumentException.class, () -> {
            service.add(q1, q2);
        });
    }
}
