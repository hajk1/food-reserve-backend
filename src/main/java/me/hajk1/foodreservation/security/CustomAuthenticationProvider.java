package me.hajk1.foodreservation.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    public CustomAuthenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
        org.springframework.security.authentication.UsernamePasswordAuthenticationToken authentication) {

        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new org.springframework.security.authentication.BadCredentialsException(
                "Bad credentials");
        }

        String presentedPassword = authentication.getCredentials().toString();
        String storedPassword = userDetails.getPassword();

        log.debug("Comparing passwords - Stored hash: {}", storedPassword);
        log.debug("Raw password length: {}", presentedPassword.length());

        if (!getPasswordEncoder().matches(presentedPassword, storedPassword)) {
            log.debug("Password comparison failed");
            throw new org.springframework.security.authentication.BadCredentialsException(
                "Bad credentials");
        }

        log.debug("Password comparison succeeded");
    }
}