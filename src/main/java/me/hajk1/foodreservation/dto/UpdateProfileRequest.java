package me.hajk1.foodreservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Profile update request")
public class UpdateProfileRequest {
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "New password (optional)", example = "newPassword123")
    private String newPassword;

    @Schema(description = "Current password (required for verification)", example = "currentPassword123")
    private String currentPassword;
}