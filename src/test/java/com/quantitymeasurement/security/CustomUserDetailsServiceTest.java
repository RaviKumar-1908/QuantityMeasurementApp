package com.quantitymeasurement.security;

import com.quantitymeasurement.model.UserEntity;
import com.quantitymeasurement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 
 * CustomUserDetailsServiceTest validates underlying interactions natively binding
 * core Spring Security abstractions alongside Database JPA Repositories functionally.
 * 
 * <p>
 * Mockito isolates pure architectural mappings explicitly preventing heavy, 
 * unneeded external relational database state generations completely inherently.
 * </p>
 */
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Executes natively initiating Mockito structural mapping boundaries logically prior 
     * executing individual independent test blocks.
     */
    @BeforeEach
    public void setup() {
        // Automatically reads mapped annotation elements logically inserting fake structures natively
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Confirms successful architectural bridging converting internally mapped Entities securely 
     * formulating standard externally viable Spring User objects inherently.
     */
    @Test
    public void testValidUserLoadingConvertsToSecurityContext() {
        // Arrange logic: Constructs basic database mock context matching known structural data forms
        UserEntity mockDbEntity = UserEntity.builder()
                .username("mockUser")
                .password("mockHash123")
                .role("ROLE_USER")
                .build();
                
        when(userRepository.findByUsername("mockUser")).thenReturn(Optional.of(mockDbEntity));

        // Act logic: Execute core mapping algorithm converting systems natively
        UserDetails resultingSecurityUser = customUserDetailsService.loadUserByUsername("mockUser");

        // Assert logic: Verify fundamental architectural variables transferred seamlessly
        assertNotNull(resultingSecurityUser, "Returned context logically mapped strictly null violating expectations natively.");
        assertEquals("mockUser", resultingSecurityUser.getUsername(), "Username structurally corrupted across internal boundary layers.");
        assertEquals("mockHash123", resultingSecurityUser.getPassword(), "Cryptographic structural hash mappings improperly transferred.");
        assertTrue(resultingSecurityUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")), 
            "Roles natively mapping authorization context dropped unexpectedly during conversion phase.");
            
        // Finalize verifying expected component inherently executed identical counts
        verify(userRepository, times(1)).findByUsername("mockUser");
    }

    /**
     * Proves negative-case handling intentionally throws correct standardized security abortion 
     * exceptions functionally blocking downstream context configurations inherently altogether.
     */
    @Test
    public void testInvalidUserLoadingThrowsArchitecturalException() {
        // Arrange logic: Represents a conceptually empty non-existent database outcome structurally
        when(userRepository.findByUsername("alienUser")).thenReturn(Optional.empty());

        // Assert logic inherently capturing structurally mandated exception throwing mechanisms natively
        assertThrows(UsernameNotFoundException.class, () -> {
            // Act logic: inherently failing load variables
            customUserDetailsService.loadUserByUsername("alienUser");
        }, "Exception contextually mandated during null entity results inherently failed structurally.");
        
        // Finalize architectural verification logically executing expected queries strictly precisely once native
        verify(userRepository, times(1)).findByUsername("alienUser");
    }
}
