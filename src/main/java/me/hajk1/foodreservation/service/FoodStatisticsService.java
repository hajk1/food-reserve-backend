package me.hajk1.foodreservation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.hajk1.foodreservation.dto.FoodOrderStatistics;
import me.hajk1.foodreservation.model.Food;
import me.hajk1.foodreservation.model.enums.ReservationStatus;
import me.hajk1.foodreservation.repository.FoodRepository;
import me.hajk1.foodreservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodStatisticsService {

  private final ReservationRepository reservationRepository;
  private final FoodRepository foodRepository;

  @Transactional(readOnly = true)
  public List<FoodOrderStatistics> getFoodOrderStatistics(LocalDate startDate, LocalDate endDate) {
    // Get order statistics from repository
    List<Object[]> statsData =
        reservationRepository.getOrderStatistics(startDate, endDate, ReservationStatus.CONFIRMED);

    // Calculate total orders
    int totalOrders = statsData.stream().mapToInt(data -> ((Number) data[1]).intValue()).sum();

    // Convert raw data to statistics objects
    return statsData.stream()
        .map(
            data -> {
              String foodId = (String) data[0];
              int orderCount = ((Number) data[1]).intValue();

              Food food =
                  foodRepository
                      .findById(Long.valueOf(foodId))
                      .orElseThrow(() -> new RuntimeException("Food not found: " + foodId));

              return FoodOrderStatistics.builder()
                  .foodId(foodId)
                  .foodName(food.getName())
                  .category(food.getCategory())
                  .totalOrders(orderCount)
                  .percentageOfTotal(calculatePercentage(orderCount, totalOrders))
                  .build();
            })
        .collect(Collectors.toList());
  }

  private double calculatePercentage(int count, int total) {
    if (total == 0) return 0.0;
    return Math.round((count * 100.0 / total) * 100.0) / 100.0; // Round to 2 decimal places
  }
}
