package com.quantitymeasurement.repository;

import com.quantitymeasurement.model.QuantityMeasurementEntity;
import com.quantitymeasurement.model.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    
    // Spring Data JPA magically implements all these methods based strictly on their names!
    List<QuantityMeasurementEntity> findByOperation(OperationType operation);
    
    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);
    
    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);
    
    long countByOperationAndIsErrorFalse(OperationType operation);
    
    List<QuantityMeasurementEntity> findByIsErrorTrue();
    
    // We can also use @Query completely customized queries
    @Query("SELECT q FROM QuantityMeasurementEntity q WHERE q.operation = :operation AND q.isError = false")
    List<QuantityMeasurementEntity> findSuccessfulByOperation(OperationType operation);
}
