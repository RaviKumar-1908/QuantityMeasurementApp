package com.quantitymeasurement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 
 * JwtUtil is a Spring-managed Component responsible for all operations
 * involving JSON Web Tokens (JWT). 
 * 
 * <p>
 * Its core responsibilities include:
 * 1. Generating a new JWT containing user authorities and claims.
 * 2. Parsing incoming JWTs from client requests.
 * 3. Validating the signature, structure, and expiration date of tokens.
 * 4. Extracting specific claims (like Username) out of the token body reliably.
 * </p>
 * 
 * <p>
 * The secret key and expiration timeout configurations are normally dynamically 
 * loaded from application.properties to ensure they can be modified independently 
 * without requiring a recompile.
 * </p>
 */
@Component
public class JwtUtil {

    /**
     * The confidential Secret Key used to symmetrically sign and verify the JWT.
     * <p>
     * Ideally injected from the application properties or an environment variable
     * for enhanced security, so it is never hardcoded directly into the compiled class.
     * </p>
     */
    @Value("${jwt.secret:MyExtremelySecretAndLongKeyForJWTGeneration1234!!}")
    private String secret;

    /**
     * The validity duration of the token (in milliseconds).
     * Defaults to 10 hours (1000 * 60 * 60 * 10).
     */
    @Value("${jwt.expiration:36000000}")
    private long jwtExpirationInMs;

    /**
     * Generates a secure cryptographic Key instance from the secret string using HMAC-SHA.
     * 
     * <p>
     * Modern versions of JJWT demand an explicit Key interface rather than a plain string 
     * parameter for signing tokens properly.
     * </p>
     * 
     * @return Generated Key object.
     */
    private Key getSigningKey() {
        // Creates an HMAC SHA key from the provided secret bytes
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extracts the Subject (Username) embedded within the JWT.
     * 
     * <p>
     * This Subject property is typically the primary identifier for standard Spring Security 
     * lookups. The method reads the claims directly and delegates to the generic `extractClaim`.
     * </p>
     * 
     * @param token The raw JWT input string from the HTTP Request.
     * @return The username embedded inside.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the 'Expiration' Claim from the token payload.
     * 
     * <p>
     * Uses this property immediately down the line to identify whether the token 
     * has timed out and should therefore be rejected.
     * </p>
     * 
     * @param token The raw JWT input string.
     * @return A standard java.util.Date pointing to the absolute expiration time.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generic extraction method leveraging higher-order functions to map 
     * internal generalized Claims into a strongly typed expected result.
     * 
     * @param <T> The anticipated return type of the specific requested Claim.
     * @param token The raw JWT token string.
     * @param claimsResolver A Functional Interface defining the mapping logic.
     * @return The extracted Claim cast implicitly to type T.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * The core underlying method triggering JJWT libraries to 
     * parse the heavily encoded input string using our configured signing Key.
     * 
     * <p>
     * If the token was tampered with, expired, or signed with an incompatible key, 
     * JJWT throws an exception here directly halting unauthorized propagation.
     * </p>
     * 
     * @param token Raw JWT String
     * @return Map-like Claims object representing the decrypted payload
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Determines whether the token has unfortunately passed its expiration date.
     * 
     * @param token The raw JWT input string
     * @return true if expired; false if currently active.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * A straightforward method delegating to the overloaded token generation function.
     * Allows consumers to effortlessly generate a token for an authenticated user 
     * without having to construct blank internal claim maps manually.
     * 
     * @param userDetails Provided Spring Security authenticated session context.
     * @return Securely formed JWT output string.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Overloaded factory method capturing custom textual names specifically for OAuth logic.
     * 
     * @param userDetails Validated Spring session string maps inherently.
     * @param displayName Custom mapped extracted name parameter strictly injected.
     * @return Fully contextualized JWT Token implicitly bearing custom 'name' claim.
     */
    public String generateToken(UserDetails userDetails, String displayName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", displayName);
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * The underlying factory method that handles physically concatenating the JWT 
     * Header, Body (Claims), and validating Cryptographic Signature.
     * 
     * <p>
     * Steps implemented:
     * 1. Bind additional arbitrary claims (currently empty).
     * 2. Subject is applied using the passed username.
     * 3. Issuance time set to the current temporal point.
     * 4. Expiration calculates mathematically against current time.
     * 5. Finally, signs the complete composition using HS256 algorithm alongside Key.
     * </p>
     * 
     * @param claims Map consisting of potential auxiliary payloads.
     * @param subject The core identifier string (Username typically).
     * @return Signed compact string representation of the JWT Token.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates whether the token matches the requested specific end-user 
     * and affirms it hasn't somehow become expired functionally.
     * 
     * <p>
     * Used by AuthenticationFilters immediately prior to officially 
     * dispatching the internal authenticated session contexts.
     * </p>
     * 
     * @param token Raw JWT input string.
     * @param userDetails Evaluated authenticated principal details.
     * @return true if fundamentally identical and unexpired, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
