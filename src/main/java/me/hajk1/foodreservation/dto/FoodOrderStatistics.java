package me.hajk1.foodreservation.dto;

import lombok.Builder;
import lombok.Data;
import me.hajk1.foodreservation.model.enums.FoodCategory;

@Data
@Builder
public class FoodOrderStatistics {
  private String foodId;
  private String foodName;
  private FoodCategory category;
  private int totalOrders;
  private double percentageOfTotal;
}
