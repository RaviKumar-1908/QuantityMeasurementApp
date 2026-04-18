package com.quantitymeasurement.security;

import com.quantitymeasurement.model.UserEntity;
import com.quantitymeasurement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 
 * CustomOAuth2UserService acts as the vital architectural integration bridging 
 * External Identity Providers (like Google) internally to our Local Database persistence.
 * 
 * <p>
 * This Service natively intercepts the HTTP Response flow immediately after Google 
 * validates the user's password and issues standard token profiles back to Spring.
 * </p>
 * 
 * <p>
 * Process Flow:
 * 1. Delegates standard initial fetching logically to Spring's `DefaultOAuth2UserService`.
 * 2. Scrutinizes the returned Profile payload (email, name, ID).
 * 3. Checks our local `UserRepository` to see if this user historically registered.
 * 4. Extensively updates or dynamically creates fundamentally new local database user records seamlessly.
 * </p>
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    /**
     * Constructs the architectural bridge injecting necessary repository mapping variables.
     * 
     * @param userRepository JPA Data Access instance querying local mappings natively.
     */
    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * The core intercepted mapping function invoked fundamentally by Spring Security dynamically.
     * 
     * <p>
     * Execution strictly loads user details inherently from the corresponding OAuth2 provider.
     * Overridden to logically capture these details explicitly to update our MySQL database immediately.
     * </p>
     * 
     * @param userRequest Original request payload contextualizing strictly which provider answered.
     * @return Fully contextualized standard OAuth2User structure passed natively forward.
     * @throws OAuth2AuthenticationException Exception thrown inherently if external loading structurally fails.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        // Native delegation retrieving raw user payload attributes cleanly.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extracts provider logically (e.g. "google", "github")
        String provider = userRequest.getClientRegistration().getRegistrationId();
        
        // Dispatches to isolated testable method structurally allowing unit testing without HTTP networks
        return processOAuth2User(provider, oAuth2User);
    }

    /**
     * Isolated processing method fundamentally enabling clean Unit Testing natively without Mocking HTTP Super calls.
     * 
     * @param provider The String representation of the OAuth2 supplier (e.g. "google").
     * @param oAuth2User The returned payload profile native object.
     * @return Identical OAuth2User natively passed back conceptually modified or synced.
     */
    public OAuth2User processOAuth2User(String provider, OAuth2User oAuth2User) {
        // Google uniquely uses "email" for identification, others might use strictly "login"
        String email = oAuth2User.getAttribute("email");
        String providerId = oAuth2User.getAttribute("sub"); // 'sub' is standard OIDC identity

        // Fallback for Github basically if email isn't perfectly mapped inherently
        if(email == null) {
            email = oAuth2User.getAttribute("login");
            providerId = String.valueOf(oAuth2User.getAttribute("id"));
        }

        // Structural validation strictly querying existing architectures logically
        Optional<UserEntity> userOptional = userRepository.findByUsername(email);

        if (userOptional.isPresent()) {
            // Logically updates corresponding provider IDs seamlessly if mismatched originally
            UserEntity user = userOptional.get();
            if (!provider.equalsIgnoreCase(user.getProvider())) {
                user.setProvider(provider.toUpperCase());
                user.setProviderId(providerId);
                userRepository.save(user);
            }
        } else {
            // Structurally spawns conceptually brand-new entities inserting logically into MySQL natively
            UserEntity newUser = UserEntity.builder()
                    .username(email)
                    .password(java.util.UUID.randomUUID().toString()) // Uses a random UUID since old DB tables might still enforce NOT NULL constraints
                    .provider(provider.toUpperCase())
                    .providerId(providerId)
                    .role("ROLE_USER")
                    .build();
            userRepository.save(newUser);
        }

        // Continues standard architectural mapping cleanly returning structural object directly
        return oAuth2User;
    }
}
