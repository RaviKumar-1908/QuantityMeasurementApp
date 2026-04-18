package com.quantitymeasurement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * AuthResponseDTO effectively structures outbound JSON payload formations explicitly 
 * encapsulating successful cryptographic token issuances naturally mapping to frontend integrations.
 * 
 * <p>
 * Serves natively abstracting internal textual mechanisms translating specifically expected 
 * token variables (JWT text alongside "Bearer" mapping requirements contextually).
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    /**
     * The compiled JSON Web Token representation textually integrating cryptographic assurances natively.
     */
    private String token;

    /**
     * Extracted messaging variables simply allowing standardized confirmation responses inherently.
     */
    private String message;
}
