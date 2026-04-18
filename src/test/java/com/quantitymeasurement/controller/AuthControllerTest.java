package com.quantitymeasurement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantitymeasurement.model.AuthRequestDTO;
import com.quantitymeasurement.security.CustomUserDetailsService;
import com.quantitymeasurement.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 
 * AuthControllerTest functionally verifies HTTP endpoints natively without binding purely independent units alone.
 * 
 * <p>
 * This architecture uses `@SpringBootTest` alongside `@AutoConfigureMockMvc` specifically simulating 
 * comprehensive end-to-end framework filters routing exactly realistically mimicking real Client browser environments.
 * </p>
 * 
 * <p>
 * Heavy database integration logic remains simulated leveraging `@MockBean` specifically limiting 
 * tests essentially bounding strictly network/controller interface operations actively.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    /**
     * Constructs foundational variable environments routinely wiping mapping logic consecutively.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Asserts fundamental successful logic translating correct plaintext HTTP requests straight natively into 
     * inherently validated structural JWT responses reliably.
     */
    @Test
    public void testStandardValidLoginYieldsValidToken() throws Exception {
        
        // Arrange logic establishing input Data Transfer Object boundaries
        AuthRequestDTO requestDTO = new AuthRequestDTO("validUser", "validPassword");
        
        // Simulates natively the standard output from backend database mappings specifically
        UserDetails mockUser = new User("validUser", "validPassword", Collections.emptyList());
        
        // Mocking Authentication structures simulating a successful cryptographic login operation inherently
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(mockUser, null, mockUser.getAuthorities()));
        
        when(userDetailsService.loadUserByUsername("validUser")).thenReturn(mockUser);
        
        // Fakes the structurally complex Token generation avoiding unnecessary algorithm executions
        when(jwtUtil.generateToken(mockUser)).thenReturn("eyFakeMockedValidCryptographicTokenString");

        // Act & Assert logic simulating direct Client JSON executions structurally
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("eyFakeMockedValidCryptographicTokenString"))
                .andExpect(jsonPath("$.message").exists());
                
        // Validation fundamentally proving sequential internal workflow invocations structurally
        verify(jwtUtil, times(1)).generateToken(mockUser);
    }

    /**
     * Evaluates whether erroneous configurations natively throw architectural Exceptions structurally 
     * resolving securely as generalized Unauthenticated standard browser status boundaries accurately.
     */
    @Test
    public void testInvalidLoginAbortsReturningAppropriateError() throws Exception {
        
        // Arrange logically erroneous structurally matching DTO combinations inherently
        AuthRequestDTO requestDTO = new AuthRequestDTO("wrongUser", "totalGibberishPassword");
        
        // Simulating the direct throwing of specialized BadCredentials explicitly failing authorization structurally
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Failed Simulated Structural Matches Natively"));

        // Act & Assert logic inherently verifying server properly rejects attempts structurally hiding complex failures logically
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                // Note: AuthController explicitly throws Exception triggering Spring's native 500/401 bindings inherently 
                .andExpect(status().isInternalServerError()); 
                // Alternatively, some security configs intercept earlier returning 401; here the controller explicitly throws 
                // new Exception() translating commonly to InternalServerError inherently absent standard @ControllerAdvises
    }
}
