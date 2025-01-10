package com.example.foodreservation.mapper;

import com.example.foodreservation.dto.FoodResponse;
import com.example.foodreservation.model.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {
    public FoodResponse toResponse(Food food) {
        return FoodResponse.builder()
            .id(food.getId())
            .name(food.getName())
            .description(food.getDescription())
            .category(food.getCategory())
            .available(food.isAvailable())
            .build();
    }
}