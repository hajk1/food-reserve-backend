package me.hajk1.foodreservation.dto;

import lombok.Builder;
import lombok.Data;
import me.hajk1.foodreservation.model.enums.FoodCategory;

@Data
@Builder
public class FoodResponse {
  private String id;
  private String name;
  private String description;
  private String imageUrl;
  private FoodCategory category;
  private Integer remainingServings;
  private boolean available;
}
