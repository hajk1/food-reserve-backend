package me.hajk1.foodreservation.dto;

import me.hajk1.foodreservation.model.enums.ReservationStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class FoodReservationResponse {
    private String id;
    private String foodId;
    private String personId;
    private LocalDate reservationDate;
    private ReservationStatus status;
}