package com.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * QuantityMeasurementEntity
 *
 * Represents the persistent database entity for auditing and historical records
 * of all measurement operations performed by the application.
 */
@Entity
@Table(name = "quantity_measurement_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double thisValue;

    @Column(nullable = false)
    private String thisUnit;

    @Column(nullable = false)
    private String thisMeasurementType;

    private Double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType operation;

    private Double resultValue;
    private String resultUnit;
    private String resultMeasurementType;
    private String resultString;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isError;

    private String errorMessage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
