package me.hajk1.foodreservation.repository;

import me.hajk1.foodreservation.model.FoodReservation;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {
    // Simulated database - in a real application, this would use JPA/Hibernate
    private final List<FoodReservation> reservations = new ArrayList<>();

    public FoodReservation save(FoodReservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    public List<FoodReservation> findByPersonIdAndDateRange(String personId, LocalDate startDate, LocalDate endDate) {
        return reservations.stream()
            .filter(reservation -> reservation.getPersonId().equals(personId))
            .filter(reservation -> {
                if (startDate != null && endDate != null) {
                    return !reservation.getReservationDate().isBefore(startDate) &&
                           !reservation.getReservationDate().isAfter(endDate);
                } else if (startDate != null) {
                    return !reservation.getReservationDate().isBefore(startDate);
                } else if (endDate != null) {
                    return !reservation.getReservationDate().isAfter(endDate);
                }
                return true;
            })
            .toList();
    }

    public void deleteById(String id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}