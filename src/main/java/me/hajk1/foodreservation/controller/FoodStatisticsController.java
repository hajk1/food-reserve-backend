package me.hajk1.foodreservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.hajk1.foodreservation.dto.FoodOrderStatistics;
import me.hajk1.foodreservation.service.FoodStatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@Tag(name = "Food Statistics", description = "APIs for food order statistics")
public class FoodStatisticsController {

  private final FoodStatisticsService foodStatisticsService;

  @GetMapping("/food-orders")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "Get food order statistics",
      description = "Get statistics about food orders within a date range")
  @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
  public ResponseEntity<List<FoodOrderStatistics>> getFoodOrderStatistics(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

    return ResponseEntity.ok(foodStatisticsService.getFoodOrderStatistics(startDate, endDate));
  }
}
