package com.quantitymeasurement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 
 * SecurityConfig encompasses the core central architectural configuration mapping 
 * for Spring Security framework integrations mapping globally across the entire Application.
 * 
 * <p>
 * This class inherently acts as the master blueprint orchestrating:
 * - CORS / CSRF Disabling policies natively mandatory for general RESTful Stateless APIs.
 * - Granular route accessibility constraints (PermitAll vs Authenticated).
 * - Registration of globally accessible beans like PasswordEncoders and AuthenticationManagers.
 * - Binding custom filters, notably our `JwtAuthenticationFilter`, overriding native execution orders.
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    /**
     * Dependency dynamically integrated via Spring constructor.
     * 
     * @param jwtAuthenticationFilter Locally developed stateless token validation filter mechanisms.
     * @param customOAuth2UserService OAuth2 DB mapping service.
     * @param oAuth2AuthenticationSuccessHandler OAuth2 JWT generator mapping.
     */
    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomOAuth2UserService customOAuth2UserService,
                          OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    }

    /**
     * The foundational SecurityFilterChain Bean structurally shaping the underlying HTTP pipeline.
     * 
     * <p>
     * Implements logic utilizing Lambda DSL configurations (modern Spring Security 6.1+ structure).
     * Operations defined:
     * 1. Completely disables CSRF since stateless architectures don't typically rely on browser cookies natively.
     * 2. Overrides default web session state machines explicitly setting `SessionCreationPolicy.STATELESS`.
     * 3. Selectively whitelists specific routes, chiefly Authentication login points and OpenAPI / Swagger interfaces.
     * 4. Forces authentication verification uniformly across all remaining application mappings.
     * 5. Implants the custom parsed `JwtAuthenticationFilter` decisively ahead of standard native UsernamePassword filters.
     * </p>
     * 
     * @param http Core internal HttpSecurity builder interface mapping internal rule states.
     * @return Fully materialized SecurityFilterChain object registering globally against Server architectures.
     * @throws Exception Anomalies structurally failing binding HTTP variables internally.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(org.springframework.security.config.Customizer.withDefaults()) // Enable CORS logically ensuring Pre-flight OPTIONS navigate authentically
            .csrf(csrf -> csrf.disable()) // Disable basic cross site (request) forgery as JWT avoids typical session exploits
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Eliminates state entirely, it is stating that the authenticaiton is no more stateful
            .authorizeHttpRequests(authz -> authz
                // Open paths structurally intended for guest access explicitly (Login, Signup, Documentation, Errors)
                .requestMatchers("/api/auth/**", "/login/oauth2/**", "/error").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Allow anyone to use the measurement features
                .requestMatchers("/api/quantities/compare", "/api/quantities/convert", "/api/quantities/add", "/api/quantities/subtract", "/api/quantities/divide").permitAll()
                // All other downstream mapped paths unconditionally mandate an established verified identity
                .anyRequest().authenticated()
            )
            // OAuth2 Login specifically injecting our custom success generator routing JSON dynamically 
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
                .successHandler(oAuth2AuthenticationSuccessHandler)
            )
            // Explicit interception defining priority execution manually over typical spring Username bindings
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Declares the global cryptographic strategy enforcing strict hashing architectures 
     * universally when processing native password persistence flows natively.
     * 
     * <p>
     * BCrypt applies salt fundamentally yielding deterministic verification but randomized storage securely.
     * </p>
     * 
     * @return Cryptographic PasswordEncoder standard implementation.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes fundamentally the internal AuthenticationManager engine natively allowing 
     * explicitly programmed Controller layer login verifications programmatically overriding standards.
     * 
     * @param authConfig Spring mapping builder natively assembling authentication variables seamlessly.
     * @return AuthenticationManager interface fundamentally integrating native and custom Provider frameworks dynamically.
     * @throws Exception Core infrastructural structural deviations inherently mapped centrally.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
