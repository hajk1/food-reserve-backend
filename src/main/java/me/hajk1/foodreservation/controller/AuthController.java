package me.hajk1.foodreservation.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.hajk1.foodreservation.dto.LoginRequest;
import me.hajk1.foodreservation.dto.RegisterRequest;
import me.hajk1.foodreservation.dto.UpdateProfileRequest;
import me.hajk1.foodreservation.dto.UserProfileResponse;
import me.hajk1.foodreservation.model.User;
import me.hajk1.foodreservation.security.CustomUserDetailsService;
import me.hajk1.foodreservation.security.JwtUtil;
import me.hajk1.foodreservation.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication and user management endpoints")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
        CustomUserDetailsService userDetailsService,
        JwtUtil jwtUtil,
        UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login with username and password",
        description = "Authenticates a user and returns a JWT token")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401)
                .body(new ErrorResponse("Invalid username or password"));
        }

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user",
        description = "Creates a new user account")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.registerNewUser(registerRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/profile")
    @Operation(summary = "Get user profile",
        description = "Retrieves the profile of the currently authenticated user",
        security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            UserProfileResponse profile = userService.getUserProfile(username);
            return ResponseEntity.ok(profile);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/profile")
    @Operation(summary = "Update user profile",
        description = "Updates the profile of the currently authenticated user",
        security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest updateRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            User user = userService.updateProfile(username, updateRequest);
            return ResponseEntity.ok(new MessageResponse("Profile updated successfully"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401)
                .body(new ErrorResponse(e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

@Data
@AllArgsConstructor
class AuthResponse {
    private String token;
}

@Data
@AllArgsConstructor
class MessageResponse {
    private String message;
}

@Data
@AllArgsConstructor
class ErrorResponse {
    private String error;
}