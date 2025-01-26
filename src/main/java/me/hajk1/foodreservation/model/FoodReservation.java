package me.hajk1.foodreservation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import me.hajk1.foodreservation.model.enums.ReservationStatus;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "food_reservations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodReservation {
  @Id private String id;

  @Column(nullable = false)
  private String foodId;

  @Column(nullable = false)
  private String personId;

  @Column(nullable = false)
  private LocalDate reservationDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReservationStatus status;
}