package me.hajk1.foodreservation.repository;

import me.hajk1.foodreservation.model.FoodReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<FoodReservation, String> {
  List<FoodReservation> findByPersonIdAndReservationDateBetween(
      String personId, LocalDate startDate, LocalDate endDate);

  List<FoodReservation> findByPersonIdAndReservationDateGreaterThanEqual(
      String personId, LocalDate startDate);

  List<FoodReservation> findByPersonIdAndReservationDateLessThanEqual(
      String personId, LocalDate endDate);

  List<FoodReservation> findByPersonId(String personId);
}