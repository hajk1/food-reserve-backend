package me.hajk1.foodreservation.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import me.hajk1.foodreservation.dto.FoodResponse;
import me.hajk1.foodreservation.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/foods")
@Tag(name = "Foods", description = "Food management APIs")
@SecurityRequirement(name = "bearerAuth")  // Can be applied at class level for all endpoints

public class FoodController {
    
    private final FoodService foodService;
    
    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }
    
    @GetMapping
    @Operation(summary = "Get available foods for a specific date")
    public List<FoodResponse> getAvailableFoods(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return foodService.getAvailableFoods(date);
    }

    @GetMapping("/secured")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("This is a secured endpoint");
    }
}