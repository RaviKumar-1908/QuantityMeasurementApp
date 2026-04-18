package com.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Implements the Entity representation of a User in the database.
 * This class stores user credentials (username, password) and role structures.
 * 
 * <p>
 * It uses Lombok annotations for boilerplate generation:
 * - \@Data for getters, setters, equals, hashcode, toString
 * - \@Builder for easy object creation
 * - \@NoArgsConstructor and \@AllArgsConstructor for required JPA instantiation
 * </p>
 *
 * <p>
 * For Authentication, this entity acts as the main data store representing valid identities
 * within the application's ecosystem.
 * </p>
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    /**
     * The primary key identifier for the user.
     * Auto-incremented sequence managed natively by the RDBMS (MySQL/H2).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique username (or email) used for identification during login.
     * Must be uniquely constrained since it acts as the primary login credential.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * The securely hashed password storage.
     * <p>
     * Modified in UC18: This is now nullable because users registering via
     * external OAuth2 providers (like Google) will not supply a local password.
     * </p>
     */
    @Column(nullable = true)
    private String password;

    /**
     * The allocated provider for the user's origin (e.g., "LOCAL", "GOOGLE").
     * <p>
     * Helps fundamentally identify whether the user should authenticate via DB passwords
     * or bypass it by relying strictly on external OAuth authenticators.
     * </p>
     */
    @Column(nullable = false)
    @Builder.Default
    private String provider = "LOCAL";

    /**
     * External supplier's universally unique identifier (e.g., Google ID).
     */
    @Column(nullable = true)
    private String providerId;

    /**
     * The assigned role string representation (e.g., "ROLE_USER", "ROLE_ADMIN").
     * Used by Spring Security authorities to enforce role-based access control rules.
     */
    @Column(nullable = false)
    private String role;
}
