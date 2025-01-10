package me.hajk1.foodreservation.mapper;

import me.hajk1.foodreservation.dto.FoodReservationResponse;
import me.hajk1.foodreservation.model.FoodReservation;
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