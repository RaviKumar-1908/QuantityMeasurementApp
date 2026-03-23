package com.quantitymeasurement.dto;

import com.quantitymeasurement.model.OperationType;
import com.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public QuantityMeasurementDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getThisValue() { return thisValue; }
    public void setThisValue(Double thisValue) { this.thisValue = thisValue; }

    public String getThisUnit() { return thisUnit; }
    public void setThisUnit(String thisUnit) { this.thisUnit = thisUnit; }

    public String getThisMeasurementType() { return thisMeasurementType; }
    public void setThisMeasurementType(String thisMeasurementType) { this.thisMeasurementType = thisMeasurementType; }

    public Double getThatValue() { return thatValue; }
    public void setThatValue(Double thatValue) { this.thatValue = thatValue; }

    public String getThatUnit() { return thatUnit; }
    public void setThatUnit(String thatUnit) { this.thatUnit = thatUnit; }

    public String getThatMeasurementType() { return thatMeasurementType; }
    public void setThatMeasurementType(String thatMeasurementType) { this.thatMeasurementType = thatMeasurementType; }

    public OperationType getOperation() { return operation; }
    public void setOperation(OperationType operation) { this.operation = operation; }

    public Double getResultValue() { return resultValue; }
    public void setResultValue(Double resultValue) { this.resultValue = resultValue; }

    public String getResultUnit() { return resultUnit; }
    public void setResultUnit(String resultUnit) { this.resultUnit = resultUnit; }

    public String getResultMeasurementType() { return resultMeasurementType; }
    public void setResultMeasurementType(String resultMeasurementType) { this.resultMeasurementType = resultMeasurementType; }

    public String getResultString() { return resultString; }
    public void setResultString(String resultString) { this.resultString = resultString; }

    public boolean isError() { return isError; }
    public void setError(boolean isError) { this.isError = isError; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

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
