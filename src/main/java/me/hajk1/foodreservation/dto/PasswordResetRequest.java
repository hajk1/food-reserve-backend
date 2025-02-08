package me.hajk1.foodreservation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequest {
  @NotBlank(message = "New password is required")
  private String newPassword;
}
