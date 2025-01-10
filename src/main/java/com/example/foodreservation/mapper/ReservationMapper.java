package com.example.foodreservation.mapper;

import com.example.foodreservation.dto.FoodReservationResponse;
import com.example.foodreservation.model.FoodReservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public FoodReservationResponse toResponse(FoodReservation reservation) {
        return FoodReservationResponse.builder()
            .id(reservation.getId())
            .foodId(reservation.getFoodId())
            .personId(reservation.getPersonId())
            .reservationDate(reservation.getReservationDate())
            .status(reservation.getStatus())
            .build();
    }
}