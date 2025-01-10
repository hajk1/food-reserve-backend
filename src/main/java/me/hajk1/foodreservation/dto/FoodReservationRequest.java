package me.hajk1.foodreservation.dto;

import me.hajk1.foodreservation.validation.FutureDate;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

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