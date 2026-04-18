package com.quantitymeasurement.security;

import com.quantitymeasurement.model.UserEntity;
import com.quantitymeasurement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 
 * CustomUserDetailsService fulfills the `UserDetailsService` contract specified by Spring Security.
 * 
 * <p>
 * This component's exclusive mandate is to facilitate communication between the underlying
 * data store (Repository) and Spring Security’s core authentication engine framework. Specifically,
 * it takes a raw username sent during login and retrieves complete User details.
 * </p>
 * 
 * <p>
 * This class also handles the necessary mapping of standard Database user roles into 
 * properly instantiated Spring Security Granted Authorities (`SimpleGrantedAuthority`).
 * Without this bridging logic, Spring Security remains completely blind to any user identities.
 * </p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Dependency Injector linking the underlying persistent storage Database 
     * context directly into Spring Security architectures.
     * 
     * @param userRepository JPA data access component mapping `users` table functionalities.
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This extremely important overriden method intercepts internal Spring Authentication Flow.
     * 
     * <p>
     * The process operates sequentially:
     * 1. Query underlying Database looking strictly by inputted username string.
     * 2. If present, translate local database `UserEntity` record mapping to Spring context.
     * 3. Construct the Granted Authorities mapping (Required for @PreAuthorize roles) 
     * 4. Wrap result back up using default concrete spring `User` instantiation models.
     * 5. Throw exceptions preventing access entirely if zero matches found against datastore.
     * </p>
     * 
     * @param username The exact username provided either by a login form or JWT claim.
     * @return Wrapped Spring framework generic UserDetails contextual session.
     * @throws UsernameNotFoundException Standard specific spring authentication abortion error indicating failure.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Attempt retrieving underlying database object via exact username.
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not centrally found in database: " + username));

        // Create standard Spring Security 'SimpleGrantedAuthority' from User string role property.   
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole());

        // Returns a fully integrated generic Spring user details representation 
        // to automatically bridge local systems with external modular standard security filters.
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
