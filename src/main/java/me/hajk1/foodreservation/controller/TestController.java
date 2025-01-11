package me.hajk1.foodreservation.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final PasswordEncoder passwordEncoder;

    public TestController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/password")
    public String testPasswordEncoder() {
        String rawPassword = "admin";
        String encoded = passwordEncoder.encode(rawPassword);
        boolean matches = passwordEncoder.matches(rawPassword, encoded);

        log.debug("Raw password: {}", rawPassword);
        log.debug("Encoded password: {}", encoded);
        log.debug("Matches: {}", matches);

        return "Password encoding test completed - check logs";
    }

    @PostMapping("/verify")
    public String verifyPassword(@RequestBody VerifyPasswordRequest request) {
        boolean matches = passwordEncoder.matches(request.getRawPassword(), request.getStoredHash());

        log.debug("Verifying raw password against stored hash");
        log.debug("Raw password length: {}", request.getRawPassword().length());
        log.debug("Stored hash: {}", request.getStoredHash());
        log.debug("Matches: {}", matches);

        return "Matches: " + matches;
    }
}

@Data
class VerifyPasswordRequest {
    private String rawPassword;
    private String storedHash;
}
