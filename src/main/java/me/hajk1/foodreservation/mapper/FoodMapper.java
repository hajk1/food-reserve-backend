package me.hajk1.foodreservation.mapper;

import me.hajk1.foodreservation.dto.FoodResponse;
import me.hajk1.foodreservation.model.DailyMenu;
import me.hajk1.foodreservation.model.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {
    public FoodResponse toResponse(Food food) {
    return FoodResponse.builder()
        .id(String.valueOf(food.getId()))
        .name(food.getName())
        .description(food.getDescription())
        .imageUrl(food.getImageUrl())
        .category(food.getCategory())
        .available(food.isAvailable())
        .build();
    }
}