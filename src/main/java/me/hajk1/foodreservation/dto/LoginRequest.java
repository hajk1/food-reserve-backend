package me.hajk1.foodreservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "Login request")
public class LoginRequest {
    @NotBlank(message = "Username is required")
    @Schema(description = "Username for authentication", example = "john.doe")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password for authentication", example = "password123")
    private String password;
}