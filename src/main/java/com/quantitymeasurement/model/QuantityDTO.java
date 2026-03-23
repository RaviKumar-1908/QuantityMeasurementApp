package com.quantitymeasurement.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuantityDTO
 *
 * Core Data Transfer Object representing a physical quantity with a scalar value,
 * a unit label, and its measurement category. Used heavily across the API and Service layer.
 */
@Data
@NoArgsConstructor
public class QuantityDTO {
    
    @NotNull(message = "Value is required")
    private Double value;
    
    @NotEmpty(message = "Unit is required")
    private String unit;
    
    @NotEmpty(message = "Measurement type is required")
    private String measurementType;

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value = value;
        this.unit = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS;
        public String getUnitName() { return name(); }
        public String getMeasurementType() { return getClass().getSimpleName(); }
    }
    
    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITRE, GALLON;
        public String getUnitName() { return name(); }
        public String getMeasurementType() { return getClass().getSimpleName(); }
    }
    
    public enum WeightUnit implements IMeasurableUnit {
        KILOGRAM, GRAM, POUND;
        public String getUnitName() { return name(); }
        public String getMeasurementType() { return getClass().getSimpleName(); }
    }
    
    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;
        public String getUnitName() { return name(); }
        public String getMeasurementType() { return getClass().getSimpleName(); }
    }
    
    @Override
    public String toString() {
        return String.format("%s %s", Double.toString(value).replace("\\.0+$", ""), unit);
    }
}
