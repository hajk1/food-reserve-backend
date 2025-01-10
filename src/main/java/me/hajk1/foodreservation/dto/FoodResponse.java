package me.hajk1.foodreservation.dto;

import me.hajk1.foodreservation.model.enums.FoodCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodResponse {
    private String id;
    private String name;
    private String description;
    private FoodCategory category;
    private boolean available;
}