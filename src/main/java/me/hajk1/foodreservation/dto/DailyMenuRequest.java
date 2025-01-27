package me.hajk1.foodreservation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import me.hajk1.foodreservation.validation.FutureDate;

@Data
public class DailyMenuRequest {
  @NotNull(message = "Food ID is required")
  private Long foodId;

  @NotNull(message = "Max servings is required")
  @Min(value = 1, message = "Max servings must be at least 1")
  private Integer maxServings;

  @NotNull(message = "Date is required")
  @FutureDate
  private LocalDate date;
}
