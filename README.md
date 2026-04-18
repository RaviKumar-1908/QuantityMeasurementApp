# 📏 QuantityMeasurementApp

> A Java Spring Boot application developed using Test-Driven Development (TDD) to progressively design and refine a quantity measurement system. The project emphasizes incremental development, clean object-oriented design, and progressive architectural refactoring to build a flexible and maintainable domain model over time.

### 📖 Overview

- Modular Java project focused on modelling multi-category quantity measurements (length, weight, and volume) with full arithmetic and conversion support.
- Organized around incremental Use Cases evolving from simple equality checks to a highly scalable Spring Boot REST API.
- Emphasizes clarity, consistency, and maintainable layered architectures as the system scales.

### ✅ Implemented Features

> _Features developed incrementally from UC1 to UC17._

- 🧩 **UC1 – Feet Equality :**
  - Implements value-based equality for feet measurements using an overridden `equals()` method.

- 🧩 **UC2 – Inches Equality :**
  - Extends value-based equality comparison to inches measurements using a dedicated `Inches` class.

- 🧩 **UC3 – Generic Length :**
  - Refactors unit-specific classes into a unified `Length` abstraction using a `LengthUnit` enum.

- 🧩 **UC4 – Extended Unit Support :**
  - Adds Yards and Centimeters to the `LengthUnit` enum with appropriate conversion factors.

- 🧩 **UC5 – Unit-to-Unit Conversion :**
  - Introduces explicit conversion operations between supported length units using centralized enum conversion factors.

- 🧩 **UC6 – Length Addition Operation :**
  - Introduces addition between length measurements with automatic unit normalization and conversion.

- 🧩 **UC7 – Addition with Target Unit Specification :**
  - Extends length addition to allow explicit specification of the result unit independent of operand units.

- 🧩 **UC8 – Standalone Unit Refactor :**
  - Extracts `LengthUnit` into a standalone enum responsible for all unit conversion logic.

- 🧩 **UC9 – Weight Measurement Support :**
  - Introduces a weight measurement category with `Weight` and `WeightUnit` supporting kilograms, grams, and pounds.

- 🧩 **UC10 – Generic Quantity Architecture :**
  - Introduces a generic `Quantity<U extends IMeasurable>` model enabling multiple measurement categories through a shared abstraction.

- 🧩 **UC11 – Volume Measurement Support :**
  - Adds a new measurement category using `VolumeUnit` (Litre, Millilitre, Gallon).

- 🧩 **UC12 – Subtraction and Division Operations :**
  - Introduces subtraction between quantities with automatic cross-unit normalization while preserving immutability.
  - Adds division support producing a dimensionless ratio.

- 🧩 **UC13 – Centralized Arithmetic Logic (DRY Refactor) :**
  - Refactors addition, subtraction, and division to use a centralized arithmetic helper.

- 🧩 **UC14 – Temperature Measurement (Selective Arithmetic Support) :**
  - Introduces temperature measurements using `TemperatureUnit`. Supports equality comparison and unit conversion across Celsius, Fahrenheit, and Kelvin.

- 🧩 **UC15 – N-Tier Architecture Refactoring :**
  - Refactors the Application from a monolithic design into a structured **N-Tier architecture**.
  - Introduces layered separation including Controller, Service, Repository, Model, Entity, and DTO packages.

- 🧩 **UC16 – Database Integration with JDBC :**
  - Extends the N-Tier architecture established in UC15 with **persistent relational database storage** using **JDBC (Java Database Connectivity)**.

- 🧩 **UC17 – Spring Boot REST API & Spring Data JPA Integration :**
  - Completely migrated the legacy standard Java POJO application into a fully managed **Spring Boot 3** web application.
  - Replaced manual JDBC configuration and connection pooling with **Spring Data JPA** and Hibernate for seamless, robust, and automatic object-relational mapping.
  - Upgraded the manual Controller layer into a modern RESTful **@RestController** serving JSON responses over HTTP at `/api/quantities`.
  - Introduced completely documented API endpoints for `/compare`, `/convert`, `/add`, `/subtract`, `/divide`, and `/history` utilizing strict `QuantityInputDTO` payloads.
  - Integrated **OpenAPI 3 / Swagger UI** for interactive exploration and execution of all application endpoints directly from the browser (`/swagger-ui.html`).
  - Restructured packages by safely isolating Data Transfer Objects and database models into a centralized `model` package (`QuantityDTO`, `QuantityInputDTO`, `QuantityMeasurementDTO`, `QuantityMeasurementEntity`).
  - Optimized the Java 17 record-like Boilerplate code utilizing **Lombok** `@Data`, `@Builder`, and generated constructor injections.

- 🧩 **UC18 – Authentication and User Management (Spring Security, JWT, OAuth2) :**
  - Fully integrated **Spring Security** to provide stateless, robust API protection across all application domains.
  - Implemented custom **JSON Web Token (JWT)** architecture for secure, session-less authentication routing seamlessly over typical HTTP architectures.
  - Enabled external **OAuth2 Social Login** (Google/GitHub) alongside traditional local username/password registration.
  - Intercepted default browser OAuth2 redirects to directly output programmatic JWTs via clean JSON responses suitable for REST APIs.
  - Established rigorous **BCrypt** password hashing for local accounts and flawless database synchronization for external OAuth2 profiles natively.

### 🧰 Tech Stack

- **Java 17+** 
- **Spring Boot 3.2.x** — Core framework for REST API and Dependency Injection
- **Spring Data JPA (Hibernate)** — Object-relational mapping for simplified database interactions
- **OpenAPI 3.0 / Swagger** — Interactive API documentation and testing UI
- **Maven** — Build automation and dependency management
- **H2 / MySQL** — Embedded in-memory database for rapid development (Configured in application.properties)
- **Lombok** — Boilerplate reduction (Auto-gen getters, setters, builders)
- **JUnit 5 & Spring Boot Test** — Integration and Application Context verification

### ▶️ Build / Run

- Clean and compile the application:
  ```bash
  mvn clean compile
  ```

- Start the Spring Boot Web Server (Locally):
  ```bash
  mvn spring-boot:run
  ```

- **Access the Application System:**
  Once the server boots up, navigate to the following URL in your web browser to test and execute commands visually:
  **`http://localhost:8080/swagger-ui.html`** 
  *(Default generated Spring Security Username: `user` | Password: See your console logs)*

- Run all Application Tests:
  ```bash
  mvn clean test
  ```

### ⚙️ Configuration

The application is securely configured via `src/main/resources/application.properties`:

```properties
# Web Server Configuration
server.port=8080

# H2 In-Memory Database (Development)
spring.datasource.url=jdbc:h2:mem:quantitymeasurementdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true

# H2 Console Web UI
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### 📂 Project Structure

```text
  📦 QuantityMeasurementApp
  │
  ├── 📁 src
  │   ├── 📁 main
  │   │   ├── 📁 java
  │   │   │   └── 📁 com
  │   │   │       └── 📁 quantitymeasurement
  │   │   │           ├── 📁 controller
  │   │   │           │   └── 📄 QuantityMeasurementController.java
  │   │   │           │
  │   │   │           ├── 📁 exception
  │   │   │           │   └── 📄 QuantityMeasurementException.java
  │   │   │           │
  │   │   │           ├── 📁 model                                       ← NEW (UC17)
  │   │   │           │   ├── 📄 OperationType.java
  │   │   │           │   ├── 📄 QuantityDTO.java
  │   │   │           │   ├── 📄 QuantityInputDTO.java
  │   │   │           │   ├── 📄 QuantityMeasurementDTO.java
  │   │   │           │   ├── 📄 QuantityMeasurementEntity.java
  │   │   │           │   └── 📄 QuantityModel.java
  │   │   │           │
  │   │   │           ├── 📁 repository                                  ← UPDATED (UC17)
  │   │   │           │   └── 📄 QuantityMeasurementRepository.java      (Spring Data JPA)
  │   │   │           │
  │   │   │           ├── 📁 service
  │   │   │           │   ├── 📄 IQuantityMeasurementService.java
  │   │   │           │   └── 📄 QuantityMeasurementServiceImpl.java
  │   │   │           │
  │   │   │           ├── 📁 unit
  │   │   │           │   ├── 📄 IMeasurable.java
  │   │   │           │   ├── 📄 SupportsArithmetic.java
  │   │   │           │   ├── 📄 LengthUnit.java
  │   │   │           │   ├── 📄 WeightUnit.java
  │   │   │           │   ├── 📄 VolumeUnit.java
  │   │   │           │   └── 📄 TemperatureUnit.java
  │   │   │           │
  │   │   │           └── 📄 QuantityMeasurementApplication.java         ← NEW (UC17)
  │   │   │
  │   │   └── 📁 resources
  │   │       ├── 📄 application.properties                            
  │   │       └── 📁 db
  │   │           └── 📄 schema.sql                                    
  │   │
  │   └── 📁 test
  │       ├── 📁 java
  │       │   └── 📁 com
  │       │       └── 📁 quantitymeasurement
  │       │           ├── 📄 QuantityMeasurementApplicationTests.java    ← NEW (UC17)
  │       │           ├── 📁 exception
  │       │           │   └── 📄 QuantityMeasurementExceptionTest.java
  │       │           └── 📁 unit
  │       │               ├── 📄 IMeasurableTest.java
  │       │               ├── 📄 LengthUnitTest.java
  │       │               ├── 📄 WeightUnitTest.java
  │       │               └── 📄 VolumeUnitTest.java
  │       │
  │       └── 📁 resources
  │           └── 📄 application.properties                          
  │
  ├── ⚙️ pom.xml
  ├── 🚫 .gitignore
  └── 📘 README.md
```

<div align="center">
✨ Incrementally developed using Test-Driven Development and architecture modernization.
</div>