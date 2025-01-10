package com.example.foodreservation.dto;

import com.example.foodreservation.model.enums.FoodCategory;
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