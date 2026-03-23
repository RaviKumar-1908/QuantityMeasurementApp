package com.quantitymeasurement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class QuantityInputDTO {
    
    @Valid
    @NotNull(message = "First quantity cannot be null")
    private QuantityDTO thisQuantity;
    
    @Valid
    @NotNull(message = "Second quantity cannot be null")
    private QuantityDTO thatQuantity;
    
    @Valid
    private QuantityDTO targetUnit;

    public QuantityInputDTO() {}

    public QuantityDTO getThisQuantity() { return thisQuantity; }
    public void setThisQuantity(QuantityDTO thisQuantity) { this.thisQuantity = thisQuantity; }

    public QuantityDTO getThatQuantity() { return thatQuantity; }
    public void setThatQuantity(QuantityDTO thatQuantity) { this.thatQuantity = thatQuantity; }

    public QuantityDTO getTargetUnit() { return targetUnit; }
    public void setTargetUnit(QuantityDTO targetUnit) { this.targetUnit = targetUnit; }
}
