package me.hajk1.foodreservation.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "User profile response")
public class UserProfileResponse {
    @Schema(description = "Username of the user", example = "john.doe")
    private String username;

    @Schema(description = "List of user roles", example = "[\"USER\"]")
    private List<String> roles;

    @Schema(description = "Account status", example = "true")
    private boolean enabled;
}