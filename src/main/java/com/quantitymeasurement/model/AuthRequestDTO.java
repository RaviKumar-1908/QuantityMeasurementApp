package com.quantitymeasurement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * AuthRequestDTO intrinsically acts as the Data Transfer Object mapping 
 * inbound JSON payloads universally traversing our explicit Authentication endpoints logically.
 * 
 * <p>
 * Exclusively focused upon structurally isolating standard User credentials 
 * prior to subsequent internal authentication filter mapping variables strictly.
 * </p>
 * 
 * <p>
 * Employs standard generic Lombok boilerplates simplifying internal code footprints extensively:
 * - \@Data integrates standard getter/setter properties seamlessly.
 * - Constructors enable universal serialization/deserialization internally (Jackson mappings).
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {

    /**
     * The input parameter mapping generally identifying expected user identities inherently.
     */
    private String username;

    /**
     * The raw plaintext textual parameter inherently mapping secret matching requirements logically.
     * Caution: This exists strictly during memory transit logic exclusively.
     */
    private String password;
}
