package com.quantitymeasurement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * 
 * OAuth2AuthenticationSuccessHandler constitutes logically the ending architectural hook strictly.
 * 
 * <p>
 * Because our application explicitly is a Backend REST API utilizing Stateless JWT architectures,
 * Spring's default behavior of redirecting browser windows back organically to an arbitrary `/home` page fails contextually.
 * </p>
 * 
 * <p>
 * Core Implementation Purpose:
 * 1. Safely intercept the HTTP Request logically succeeding a completely successful Google/Github login event natively.
 * 2. Explicitly map the resulting `OAuth2User` email identity back directly into our `JwtUtil` cryptographic structural generators.
 * 3. Intercept the HTTP Response fundamentally averting logical browser redirects.
 * 4. Simply cleanly compose a RAW JSON payload physically pasting the JWT string strictly returning control logically to clients organically.
 * </p>
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    /**
     * Integrates required JWT cryptographic functionalities strictly.
     * 
     * @param jwtUtil The primary factory translating contexts fundamentally securely natively.
     */
    @Autowired
    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Fundamentally overrides default redirection logical architectures executing custom token issuance workflows securely.
     * 
     * @param request The complete underlying originating logical HTTP request context universally.
     * @param response Standard Response Interface naturally buffering output logically mapping back to original clients.
     * @param authentication Fully validated structural Spring Authentication Context natively embedding Google Profiles essentially.
     * @throws IOException I/O aberrations mapping directly during physical output buffer writes explicitly.
     * @throws ServletException Broad servlet structural irregularities handling logical context inherently.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
            throws IOException, ServletException {
        
        // Isolating natively mapped OAuth2 payload object structured organically during login logically
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        
        // Translating fundamentally provider-specific identity properties correctly standardizing structures
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        if (email == null) {
            email = oAuth2User.getAttribute("login"); // Github Fallback fundamentally
        }
        if (name == null) {
            name = email;
        }

        // Structurally constructs normalized basic Spring Contexts securely mapped integrating cleanly
        UserDetails userDetails = new User(email, "", Collections.emptyList());

        // Fundamentally signs internally mapped logically claims securely returning natively JWT structures
        String jwtToken = jwtUtil.generateToken(userDetails, name);

        // Preempts physical browser redirections cleanly mutating the Output stream universally
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Enable proper HTTP redirect cleanly pointing towards Frontend Application explicitly mapping token payloads
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:5174/login?token=" + jwtToken);
        
        // Prevent strictly any subsequent class super architectures natively executing redirects cleanly
        clearAuthenticationAttributes(request);
    }
}
