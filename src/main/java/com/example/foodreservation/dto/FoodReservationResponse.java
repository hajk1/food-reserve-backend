package com.example.foodreservation.dto;

import com.example.foodreservation.model.enums.ReservationStatus;
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