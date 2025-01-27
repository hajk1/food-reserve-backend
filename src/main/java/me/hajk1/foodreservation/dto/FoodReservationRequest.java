package me.hajk1.foodreservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import me.hajk1.foodreservation.validation.FutureDate;

@Data
public class FoodReservationRequest {
    @NotBlank(message = "Food ID is required")
    private String foodId;

    @NotBlank(message = "Person ID is required")
    private String personId;

    @NotNull(message = "Reservation date is required")
    @FutureDate
    private LocalDate reservationDate;
}