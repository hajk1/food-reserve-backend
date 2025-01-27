package me.hajk1.foodreservation.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response details")
public class ErrorResponse {
  @Schema(description = "Error message")
  private String message;

  @Schema(description = "Error code")
  private String code;

  @Schema(description = "Timestamp of the error")
  private long timestamp;
}
