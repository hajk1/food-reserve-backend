package me.hajk1.foodreservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.hajk1.foodreservation.dto.PasswordResetRequest;
import me.hajk1.foodreservation.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "APIs for user management operations")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PutMapping("/{username}/disable")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "Disable user",
      description = "Disable a user account by username. Only accessible by admins.")
  @ApiResponse(responseCode = "200", description = "User successfully disabled")
  @ApiResponse(responseCode = "404", description = "User not found")
  @ApiResponse(responseCode = "400", description = "Cannot disable last admin")
  public ResponseEntity<Void> disableUser(@PathVariable String username) {
    userService.disableUser(username);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{username}/enable")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "Enable user",
      description = "Enable a user account by username. Only accessible by admins.")
  @ApiResponse(responseCode = "200", description = "User successfully enabled")
  @ApiResponse(responseCode = "404", description = "User not found")
  public ResponseEntity<Void> enableUser(@PathVariable String username) {
    userService.enableUser(username);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{username}/reset-password")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "Reset user password",
      description = "Reset a user's password by username. Only accessible by admins.")
  @ApiResponse(responseCode = "200", description = "Password successfully reset")
  @ApiResponse(responseCode = "404", description = "User not found")
  public ResponseEntity<Void> resetPassword(
      @PathVariable String username, @Valid @RequestBody PasswordResetRequest request) {
    userService.resetPassword(username, request);
    return ResponseEntity.ok().build();
  }
}
