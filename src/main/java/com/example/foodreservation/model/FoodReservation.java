package com.example.foodreservation.model;

import com.example.foodreservation.model.enums.ReservationStatus;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodReservation {
    private String id;
    private String foodId;
    private String personId;
    private LocalDate reservationDate;
    private ReservationStatus status;
}