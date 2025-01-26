package me.hajk1.foodreservation.mapper;

import me.hajk1.foodreservation.dto.DailyMenuResponse;
import me.hajk1.foodreservation.dto.FoodResponse;
import me.hajk1.foodreservation.model.DailyMenu;
import me.hajk1.foodreservation.model.Food;
import org.springframework.stereotype.Component;

@Component
public class DailyMenuMapper {
  public DailyMenuResponse toResponse(DailyMenu dailyMenu) {
    return DailyMenuResponse.builder()
        .id(dailyMenu.getId())
        .remainingServings(dailyMenu.getRemainingServings())
        .maxServings(dailyMenu.getMaxServings())
        .date(dailyMenu.getDate())
        .food(dailyMenu.getFood())
        .build();
  }
}
