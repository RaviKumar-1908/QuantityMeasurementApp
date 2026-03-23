package com.quantitymeasurement.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuantityMeasurementDTO {
    private Long id;
    private Double thisValue;
    private String thisUnit;
    private String thisMeasurementType;
    private Double thatValue;
    private String thatUnit;
    private String thatMeasurementType;
    private OperationType operation;
    private Double resultValue;
    private String resultUnit;
    private String resultMeasurementType;
    private String resultString;
    private boolean isError;
    private String errorMessage;
    private LocalDateTime createdAt;

    /**
     * Converts a full JPA Entity object into an outgoing API response object.
     */
    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
        if (entity == null) return null;
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setId(entity.getId());
        dto.setThisValue(entity.getThisValue());
        dto.setThisUnit(entity.getThisUnit());
        dto.setThisMeasurementType(entity.getThisMeasurementType());
        dto.setThatValue(entity.getThatValue());
        dto.setThatUnit(entity.getThatUnit());
        dto.setThatMeasurementType(entity.getThatMeasurementType());
        dto.setOperation(entity.getOperation());
        dto.setResultValue(entity.getResultValue());
        dto.setResultUnit(entity.getResultUnit());
        dto.setResultMeasurementType(entity.getResultMeasurementType());
        dto.setResultString(entity.getResultString());
        dto.setError(entity.isError());
        dto.setErrorMessage(entity.getErrorMessage());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
        if (entities == null) return null;
        return entities.stream().map(QuantityMeasurementDTO::fromEntity).collect(Collectors.toList());
    }
}
