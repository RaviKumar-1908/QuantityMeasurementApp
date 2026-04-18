package com.quantitymeasurement.security;

import com.quantitymeasurement.model.UserEntity;
import com.quantitymeasurement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 
 * CustomOAuth2UserServiceTest functionally zeroes in on the Database Synchronization mechanics 
 * explicitly bypassing the complex native HTTP calls ordinarily triggered by `DefaultOAuth2UserService`.
 * 
 * <p>
 * By strictly isolating `processOAuth2User`, we assert definitively that Google login 
 * emails trigger brand new User constructions or gracefully update existing entities seamlessly.
 * </p>
 */
public class CustomOAuth2UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomOAuth2UserService customOAuth2UserService;

    @Mock
    private OAuth2User oAuth2User;

    /**
     * Resets architectural testing frameworks isolating Mock interactions routinely natively.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Asserts structural workflow inserting a logically fresh new user directly into mapped repositories natively.
     */
    @Test
    public void testProcessOAuth2UserCreatesNewEntityWhenNotFound() {
        // Arrange logic: simulating Google mapping inputs structurally
        when(oAuth2User.getAttribute("email")).thenReturn("new.google@example.com");
        when(oAuth2User.getAttribute("sub")).thenReturn("100200300");
        
        // Simulating absolutely zero existing historical footprints
        when(userRepository.findByUsername("new.google@example.com")).thenReturn(Optional.empty());

        // Act logic: Invoke isolated logic
        OAuth2User resultingUser = customOAuth2UserService.processOAuth2User("google", oAuth2User);

        // Assert logic verifying flawless architectural saves
        verify(userRepository, times(1)).save(any(UserEntity.class));
        assertEquals(oAuth2User, resultingUser, "Payload object radically mutated incorrectly internally.");
    }

    /**
     * Validates structurally mapping logic intelligently updates existing UserEntity structures 
     * seamlessly switching authentication Providers naturally.
     */
    @Test
    public void testProcessOAuth2UserUpdatesExistingEntityProvider() {
        // Arrange logic: Simulate matching inputs
        when(oAuth2User.getAttribute("email")).thenReturn("existing@example.com");
        when(oAuth2User.getAttribute("sub")).thenReturn("400500600");

        // Simulate an originally LOCAL historically registered mapping fundamentally
        UserEntity existingUser = UserEntity.builder()
                .username("existing@example.com")
                .provider("LOCAL")
                .build();

        when(userRepository.findByUsername("existing@example.com")).thenReturn(Optional.of(existingUser));

        // Act logic: Execute sync 
        customOAuth2UserService.processOAuth2User("google", oAuth2User);

        // Assert logic verifying save was triggered cleanly upgrading the existing entity
        verify(userRepository, times(1)).save(existingUser);
        assertEquals("GOOGLE", existingUser.getProvider(), "Provider failed to synchronize successfully across boundaries.");
        assertEquals("400500600", existingUser.getProviderId(), "Security identification structurally absent.");
    }
}
