package com.quantitymeasurement.controller;

import com.quantitymeasurement.model.AuthRequestDTO;
import com.quantitymeasurement.model.AuthResponseDTO;
import com.quantitymeasurement.model.UserEntity;
import com.quantitymeasurement.repository.UserRepository;
import com.quantitymeasurement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * AuthController centrally maps architectural incoming HTTP Authentication requests natively.
 * 
 * <p>
 * This Controller fundamentally sits structurally outside standard `SecurityFilterChain` blocking,
 * expressly permitting anonymous guests to execute Account structures or generate access tokens.
 * </p>
 * 
 * <p>
 * Core functionality implements two major pathways natively:
 * 1. `/register`: Ingests plaintext combinations, scrambles fundamentally employing PasswordEncoders, and persists.
 * 2. `/login`: Verifies explicitly matching stored encoded secrets natively returning Cryptographic JWTs structurally.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5174")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Dependency injected architectural constructor binding natively needed overarching services.
     * 
     * @param authenticationManager Standard Spring Security engine natively driving verification validations.
     * @param userDetailsService Internal loading strategy structurally retrieving User objects seamlessly.
     * @param jwtUtil Factory utility logically encapsulating Token issuance cryptographic algorithms.
     * @param userRepository Data access DAO fundamentally storing users sequentially.
     * @param passwordEncoder Cryptographic hashing logically mitigating plaintext database breaches structurally.
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Endpoint structurally defining Registration logic workflows naturally mapping into databases.
     * 
     * <p>
     * Implements sequential architecture mitigating duplicates logically whilst encrypting passwords.
     * Defaults identically to "ROLE_USER" logically granting standard endpoint accessibility later.
     * </p>
     * 
     * @param authRequest Immutable Data structure representing parsed incoming JSON inputs natively.
     * @return Standard Response contextualizing registration success or duplicated error pathways cleanly.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequestDTO authRequest) {
        
        // Fast-fail constraint identically stopping structurally identical registrations seamlessly mapping.
        if (userRepository.existsByUsername(authRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already actively taken internally!");
        }

        // Structurally constructs Entity mappings isolating domain variables natively.
        UserEntity user = UserEntity.builder()
                .username(authRequest.getUsername())
                // Irreversibly hashes secrets fundamentally rendering Database dumps logically useless inherently.
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role("ROLE_USER")
                .build();

        // Flush directly persisting mapping physically into underlying storage abstractions.
        userRepository.save(user);

        return ResponseEntity.ok("User essentially registered successfully internally via architectures!");
    }

    /**
     * Core application login mechanism inherently trading structurally sound credentials for stateless JWT implementations.
     * 
     * <p>
     * Logic strictly offloads structural validity natively relying upon Spring Security AuthenticationManager.
     * Post-verification seamlessly delegates token cryptographic mapping returning standard DTO formats inherently.
     * </p>
     * 
     * @param authRequest Immutable credential payload structurally containing mapping identities natively.
     * @return Cryptographically validated structured JWT dynamically allowing subsequent endpoint access seamlessly.
     * @throws Exception Anomalies structurally failing binding HTTP variables internally.
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest) throws Exception {

        try {
            // Defers logically mapping textual string variables natively into Spring standard validators.
            // If the hashed database combination structurally invalidates, standard Authentication Exceptions inherently propagate automatically.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Explicitly handling failing structures sending appropriate unauthorized standardized constraints inherently.
            throw new Exception("Incorrect username logically combined alongside password mapping inherently", e);
        }

        // Retrieves effectively matched contextual user structure natively loading roles logically.
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getUsername());

        // Fundamentally signs internally mapped claims issuing contextual string dynamically natively while appending user handle correctly
        final String jwt = jwtUtil.generateToken(userDetails, authRequest.getUsername());

        // Dispatches structural mapping cleanly contextualizing successful payload outputs.
        return ResponseEntity.ok(new AuthResponseDTO(jwt, "Token implicitly issued successfully strictly!"));
    }
}
