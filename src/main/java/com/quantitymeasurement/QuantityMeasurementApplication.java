package com.quantitymeasurement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Quantity Measurement API",
        version = "v1",
        description = "REST API for Quantity Measurement via Spring Boot (UC17)"
    )
)
public class QuantityMeasurementApplication {
    public static void main(String[] args) {
        // Launches the embedded Tomcat web server 
        // and initializes all Spring Boot components automatically!
        SpringApplication.run(QuantityMeasurementApplication.class, args);
    }
}
