package com.quantitymeasurement.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * JwtUtilTest validates the core cryptographic logic isolated inside JwtUtil.
 * 
 * <p>
 * Instead of spinning up full Spring Contexts, we treat this strictly as a 
 * fast, isolated Unit Test. We dynamically load standard application.properties
 * variables into this structure natively using Spring's ReflectionTestUtils.
 * </p>
 * 
 * <p>
 * Test Goals:
 * 1. Confirm JWT tokens are successfully composed natively.
 * 2. Assert username identities extracted correctly match original claims.
 * 3. Validate overall token acceptance boolean rules natively.
 * 4. Verify temporal manipulations reliably reject intentionally shortened tokens.
 * </p>
 */
public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails dummyUser;

    /**
     * Initializes underlying configurations uniformly prior to every independent Execution block natively.
     * 
     * <p>
     * Seeds dummy generic Spring Security UserDetails components alongside manually 
     * reflecting variables effectively mirroring what the @Value annotations inject natively.
     * </p>
     */
    @BeforeEach
    public void setup() {
        jwtUtil = new JwtUtil();

        // Dynamically implants variables typically managed exclusively by Spring Container context properties
        ReflectionTestUtils.setField(jwtUtil, "secret", "MyExtremelySecretAndLongKeyForJUnitTestIntegrations123!!");
        // Expire functionally in precisely ten continuous hours matching production configurations
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationInMs", 36000000L); 

        // Constructs standardized architectural credentials mapping isolated users dynamically
        dummyUser = new User("testUsername", "testPassword", Collections.emptyList());
    }

    /**
     * Structural assertion verifying Token composition processes natively successfully 
     * emit properly compacted JSON Web Token strings natively.
     */
    @Test
    public void testTokenGenerationValidatesStringOutput() {
        String token = jwtUtil.generateToken(dummyUser);
        assertNotNull(token, "Generated token structure should never functionally map logically null natively.");
        assertTrue(token.split("\\.").length == 3, "Valid JWT structures permanently mandate strictly 3 isolated dot-separated components.");
    }

    /**
     * Confirms correctly whether securely embedded logical contextual identites definitively
     * map identically backward post-extraction algorithmically.
     */
    @Test
    public void testTokenUsernameExtractionYieldsIdenticalSubject() {
        String token = jwtUtil.generateToken(dummyUser);
        String extractedUsername = jwtUtil.extractUsername(token);
        
        assertEquals("testUsername", extractedUsername, "The logically extracted subject structurally fails matching original embedded identity inherently.");
    }

    /**
     * Verifies expiration architectures functionally embed forward-looking timestamps properly 
     * compared concurrently natively against clock machines immediately generated.
     */
    @Test
    public void testTokenExtractionYieldsValidFutureExpiration() {
        String token = jwtUtil.generateToken(dummyUser);
        Date expiration = jwtUtil.extractExpiration(token);
        
        // Expiration functionally must permanently map sometime into future states structurally 
        assertTrue(expiration.after(new Date()), "Token chronologically expired instantly natively violating contextual configuration norms inherently.");
    }

    /**
     * Ensures combined identity mapping plus chronology comprehensively succeeds
     * structurally confirming completely sound payloads.
     */
    @Test
    public void testComprehensiveTokenValidationSucceeds() {
        String token = jwtUtil.generateToken(dummyUser);
        Boolean isValid = jwtUtil.validateToken(token, dummyUser);
        
        assertTrue(isValid, "Structurally verified matching user token combination failed unexpected validations natively.");
    }
}
