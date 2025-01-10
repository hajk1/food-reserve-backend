package com.example.foodreservation.repository;

import com.example.foodreservation.model.Food;
import com.example.foodreservation.model.enums.FoodCategory;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

@Repository
public class FoodRepository {
    // Simulated database - in a real application, this would use JPA/Hibernate
    public List<Food> findAvailableFoodsByDate(LocalDate date) {
        return Arrays.asList(
            Food.builder()
                .id("1")
                .name("Spaghetti Carbonara")
                .description("Classic Italian pasta dish")
                .category(FoodCategory.MAIN_COURSE)
                .available(true)
                .build(),
            Food.builder()
                .id("2")
                .name("Caesar Salad")
                .description("Fresh romaine lettuce with Caesar dressing")
                .category(FoodCategory.SALAD)
                .available(true)
                .build()
        );
    }
}