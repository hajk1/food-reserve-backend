package me.hajk1.foodreservation.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import me.hajk1.foodreservation.model.Food;
import me.hajk1.foodreservation.model.enums.FoodCategory;

@Data
@Builder
public class DailyMenuResponse {
  private Long id;
  private Food food;
  private LocalDate date;
  private Integer maxServings;
  private Integer remainingServings;
}
