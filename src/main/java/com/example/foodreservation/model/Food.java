package com.example.foodreservation.model;

import com.example.foodreservation.model.enums.FoodCategory;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    private String id;
    private String name;
    private String description;
    private FoodCategory category;
    private boolean available;
}