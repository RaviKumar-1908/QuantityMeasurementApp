package com.quantitymeasurement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.dto.QuantityInputDTO;
import com.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.quantitymeasurement.service.IQuantityMeasurementService;
import com.quantitymeasurement.repository.QuantityMeasurementRepository;

/**
 * QuantityMeasurementController
 *
 * REST Controller exposing quantity measurement operations as API endpoints.
 */
@RestController
@RequestMapping("/api/quantities")
@Tag(name = "Quantity Measurement API", description = "Endpoints for comparing, converting, and performing arithmetic on physical quantities.")
public class QuantityMeasurementController {

    private static final Logger logger = Logger.getLogger(QuantityMeasurementController.class.getName());

    private final IQuantityMeasurementService service;
    private final QuantityMeasurementRepository repository;

    @Autowired
    public QuantityMeasurementController(IQuantityMeasurementService service, QuantityMeasurementRepository repository) {
        this.service = service;
        this.repository = repository;
        logger.info("QuantityMeasurementController initialized as REST Controller.");
    }

    /**
     * POST endpoint to compare two quantities.
     *
     * @param input Data Transfer Object containing both quantities
     * @return boolean wrapped in ResponseEntity indicating equality
     */
    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities", description = "Returns true if the two quantities are physically equal.")
    public ResponseEntity<Boolean> compare(@Valid @RequestBody QuantityInputDTO input) {
        boolean result = service.compare(input.getThisQuantity(), input.getThatQuantity());
        return ResponseEntity.ok(result);
    }

    /**
     * POST endpoint to convert a quantity into a target unit.
     *
     * @param input Data Transfer Object containing the source and target items
     * @return Converted quantity wrapped in ResponseEntity
     */
    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity", description = "Converts the first quantity into the unit of the target quantity.")
    public ResponseEntity<QuantityDTO> convert(@Valid @RequestBody QuantityInputDTO input) {
        // targetUnit or thatQuantity can act as the target
        QuantityDTO target = input.getTargetUnit() != null ? input.getTargetUnit() : input.getThatQuantity();
        QuantityDTO result = service.convert(input.getThisQuantity(), target);
        return ResponseEntity.ok(result);
    }

    /**
     * POST endpoint to add two quantities together.
     *
     * @param input Data Transfer Object containing the operands
     * @return Summed quantity wrapped in ResponseEntity
     */
    @PostMapping("/add")
    @Operation(summary = "Add two quantities", description = "Adds two quantities and optionally returns the result in a target unit.")
    public ResponseEntity<QuantityDTO> add(@Valid @RequestBody QuantityInputDTO input) {
        QuantityDTO result;
        if (input.getTargetUnit() != null) {
            result = service.add(input.getThisQuantity(), input.getThatQuantity(), input.getTargetUnit());
        } else {
            result = service.add(input.getThisQuantity(), input.getThatQuantity());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * POST endpoint to subtract one quantity from another.
     *
     * @param input Data Transfer Object containing the operands
     * @return Subtracted quantity wrapped in ResponseEntity
     */
    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities", description = "Subtracts the second quantity from the first.")
    public ResponseEntity<QuantityDTO> subtract(@Valid @RequestBody QuantityInputDTO input) {
        QuantityDTO result;
        if (input.getTargetUnit() != null) {
            result = service.subtract(input.getThisQuantity(), input.getThatQuantity(), input.getTargetUnit());
        } else {
            result = service.subtract(input.getThisQuantity(), input.getThatQuantity());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * POST endpoint to divide one quantity by another.
     *
     * @param input Data Transfer Object containing the operands
     * @return Division ratio wrapped in ResponseEntity
     */
    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities", description = "Divides the first quantity by the second and returns the numerical ratio.")
    public ResponseEntity<Double> divide(@Valid @RequestBody QuantityInputDTO input) {
        double result = service.divide(input.getThisQuantity(), input.getThatQuantity());
        return ResponseEntity.ok(result);
    }

    /**
     * GET endpoint to fetch the full database operation history.
     *
     * @return List of past operations wrapped in ResponseEntity
     */
    @GetMapping("/history")
    @Operation(summary = "Get measurement history", description = "Retrieves all past operations stored in the database.")
    public ResponseEntity<List<QuantityMeasurementDTO>> getHistory() {
        List<QuantityMeasurementDTO> history = QuantityMeasurementDTO.fromEntityList(repository.findAll());
        return ResponseEntity.ok(history);
    }
}