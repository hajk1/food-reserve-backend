package me.hajk1.foodreservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import me.hajk1.foodreservation.model.enums.FoodCategory;

@Data
public class FoodRequest {
  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Description is required")
  private String description;

  @NotNull(message = "Category is required")
  private FoodCategory category;

  private boolean available = true;
}
