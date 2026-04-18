package com.quantitymeasurement.repository;

import com.quantitymeasurement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 
 * Implements the Data Access Object (DAO) via Spring Data JPA for User entities.
 * 
 * <p>
 * This interface dynamically exposes complete CRUD capabilities for the `users` table
 * and allows defining custom query methods by simply declaring method signatures.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Custom Query Method to find a user directly by their registered username.
     * 
     * <p>
     * Under the hood, Spring Data JPA creates an implementation equivalent to:
     * `SELECT * FROM users WHERE username = ?`
     * </p>
     *
     * @param username The exact username sequence to retrieve.
     * @return An Optional wrapping the found `UserEntity` or `Optional.empty()` if nonexistent.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Custom Query Method to verify if any user currently exists in the DB with the provided username.
     * 
     * <p>
     * This query is optimized to return a simple boolean rather than fetching the whole record,
     * which is highly useful during Registration/Sign-up to fail fast on duplicates.
     * </p>
     * 
     * @param username The username to check.
     * @return boolean flag true if exists, false otherwise.
     */
    boolean existsByUsername(String username);
}
