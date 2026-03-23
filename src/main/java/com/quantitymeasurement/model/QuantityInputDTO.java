package com.quantitymeasurement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuantityInputDTO {
    
    @Valid
    @NotNull(message = "First quantity cannot be null")
    private QuantityDTO thisQuantity;
    
    @Valid
    @NotNull(message = "Second quantity cannot be null")
    private QuantityDTO thatQuantity;
    
    @Valid
    private QuantityDTO targetUnit; // Optional for addition/subtraction
}
