package me.hajk1.foodreservation.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import me.hajk1.foodreservation.dto.DailyMenuResponse;
import me.hajk1.foodreservation.dto.FoodRequest;
import me.hajk1.foodreservation.dto.FoodReservationRequest;
import me.hajk1.foodreservation.dto.FoodResponse;
import me.hajk1.foodreservation.exception.ResourceNotFoundException;
import me.hajk1.foodreservation.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @Operation(
      summary = "Add food to daily menu",
      description = "Adds a new food item to the daily available menu")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Food added successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
    @ApiResponse(responseCode = "403", description = "Unauthorized access")
  })
  @PostMapping("/daily")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<DailyMenuResponse> addFoodToDaily(
      @Valid @RequestBody FoodReservationRequest request) throws ResourceNotFoundException {
    return new ResponseEntity<>(foodService.addFoodToDaily(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Add new food")
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FoodResponse> addFood(@Valid @RequestBody FoodRequest request) {
    return new ResponseEntity<>(foodService.addFood(request), HttpStatus.CREATED);
  }

  @Operation(summary = "Update food")
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FoodResponse> updateFood(
      @PathVariable String id, @Valid @RequestBody FoodRequest request)
      throws ResourceNotFoundException {
    return ResponseEntity.ok(foodService.updateFood(id, request));
  }
}