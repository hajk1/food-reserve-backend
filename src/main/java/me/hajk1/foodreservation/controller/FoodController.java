package me.hajk1.foodreservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import me.hajk1.foodreservation.dto.DailyMenuRequest;
import me.hajk1.foodreservation.dto.DailyMenuResponse;
import me.hajk1.foodreservation.dto.FoodRequest;
import me.hajk1.foodreservation.dto.FoodResponse;
import me.hajk1.foodreservation.exception.ErrorResponse;
import me.hajk1.foodreservation.service.FoodService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
@Tag(name = "Food Management", description = "APIs for managing food items and daily menus")
@SecurityRequirement(name = "bearerAuth")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

  @Operation(
      summary = "Get all foods (Admin)",
      description = "Retrieves all food items in the system. Accessible only by administrators.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved all foods",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = FoodResponse.class))),
    @ApiResponse(
        responseCode = "403",
        description = "Access denied - requires admin role",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/admin/all")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<FoodResponse>> getAllFoods() {
    return ResponseEntity.ok(foodService.getAllFoods());
  }

  @Operation(
      summary = "Create new food (Admin)",
      description = "Creates a new food item in the system. Accessible only by administrators.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Food successfully created",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = FoodResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request body",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(
        responseCode = "403",
        description = "Access denied - requires admin role",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FoodResponse> createFood(
      @Valid @RequestBody @Parameter(description = "Food details", required = true)
          FoodRequest request) {
    return new ResponseEntity<>(foodService.addFood(request), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Update existing food (Admin)",
      description =
          "Updates an existing food item in the system. Accessible only by administrators.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Food successfully updated",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = FoodResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "Food not found",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(
        responseCode = "403",
        description = "Access denied - requires admin role",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/admin/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FoodResponse> updateFood(
      @Parameter(description = "Food ID", required = true) @PathVariable String id,
      @Valid @RequestBody @Parameter(description = "Updated food details", required = true)
          FoodRequest request) {
    return ResponseEntity.ok(foodService.updateFood(id, request));
  }

  @Operation(
      summary = "Add food to daily menu (Admin)",
      description =
          "Adds a food item to the daily menu for a specific date. Accessible only by administrators.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Food successfully added to daily menu",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = DailyMenuResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "Food not found",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(
        responseCode = "403",
        description = "Access denied - requires admin role",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/admin/daily-menu")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<DailyMenuResponse> addFoodToDaily(
      @Valid @RequestBody DailyMenuRequest request) {
    return ResponseEntity.ok(foodService.addFoodToDaily(request));
  }

  @Operation(
      summary = "Get available foods for date",
      description = "Retrieves all available foods for a specific date from the daily menu.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved available foods",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = FoodResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid date format",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/available")
  public ResponseEntity<List<FoodResponse>> getAvailableFoods(
      @Parameter(description = "Date to check availability (format: YYYY-MM-DD)", required = true)
          @RequestParam
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate date) {
    return ResponseEntity.ok(foodService.getAvailableFoodsByDate(date));
  }

  @DeleteMapping("/admin/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "Delete a daily menu",
      description = "Delete a daily menu by its ID. Only admin users can perform this operation.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Daily menu successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Daily menu not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to delete daily menu")
      })
  public ResponseEntity<Void> deleteDailyMenu(@PathVariable Long id) {
    foodService.deleteDailyMenu(id);
    return ResponseEntity.noContent().build();
  }
}