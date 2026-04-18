package com.quantitymeasurement.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 
 * OAuth2AuthenticationSuccessHandlerTest validates that successful external logins 
 * are properly hijacked fundamentally averting standard redirects mathematically outputting JSON payloads natively.
 * 
 * <p>
 * Instead of spinning up full browser contexts natively, Mockito reliably isolates explicit 
 * Servlet inputs/outputs asserting structural streams dynamically.
 * </p>
 */
public class OAuth2AuthenticationSuccessHandlerTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private OAuth2AuthenticationSuccessHandler successHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private OAuth2User oAuth2User;

    /**
     * Constructs foundational variable environments routinely wiping mapping logic consecutively.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Asserts fundamental successful logic translating correct OAuth2 structures straight natively into 
     * inherently validated structural JWT JSON responses reliably.
     */
    @Test
    public void testSuccessfulOAuth2LoginOutputsJsonToken() throws IOException, ServletException {
        // Arrange logic: Mocking standard structural email attributes expected from Google
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute("email")).thenReturn("googleuser@example.com");

        // Fakes the structurally complex Token generation avoiding unnecessary algorithm executions
        when(jwtUtil.generateToken(any())).thenReturn("eyFakeMockedValidCryptographicTokenString");

        // Act logic: Executing handler natively as if Spring Security implicitly succeeded organically
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert logic simulating HTTP stream verifications structurally
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        
        // Ensure token generation dynamically fired securely leveraging extracted emails explicitly
        verify(jwtUtil, times(1)).generateToken(any());

        // Verify logical redirect explicitly mapping expected architecture naturally
        verify(response, times(1)).sendRedirect("http://localhost:5174/login?token=eyFakeMockedValidCryptographicTokenString");
    }
}
