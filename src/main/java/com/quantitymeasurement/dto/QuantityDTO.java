package com.quantitymeasurement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * QuantityDTO
 */
public class QuantityDTO {
    
    @NotNull(message = "Value is required")
    @Schema(example = "12.0", description = "Numerical value size")
    private Double value;
    
    @NotEmpty(message = "Unit is required")
    @Schema(example = "INCHES", description = "The precise Enum unit of measurement")
    private String unit;
    
    @NotEmpty(message = "Measurement type is required")
    @Schema(example = "LengthUnit", description = "The isolated Category Enum of measurement")
    private String measurementType;

    public QuantityDTO() {
    }

    public QuantityDTO(Double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public QuantityDTO(Double value, IMeasurableUnit unit) {
        this.value = value;
        this.unit = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }

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
