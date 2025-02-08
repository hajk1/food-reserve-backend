package me.hajk1.foodreservation.repository;

import java.time.LocalDate;
import java.util.List;
import me.hajk1.foodreservation.model.FoodReservation;
import me.hajk1.foodreservation.model.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<FoodReservation, String> {
  List<FoodReservation> findByPersonIdAndReservationDateBetweenAndStatus(
      String personId, LocalDate startDate, LocalDate endDate, ReservationStatus status);

  List<FoodReservation> findByPersonIdAndReservationDateGreaterThanEqualAndStatus(
      String personId, LocalDate startDate, ReservationStatus status);

  List<FoodReservation> findByPersonIdAndReservationDateLessThanEqualAndStatus(
      String personId, LocalDate endDate, ReservationStatus status);

  List<FoodReservation> findByPersonIdAndStatus(String personId, ReservationStatus status);

  boolean existsByFoodIdAndStatusAndReservationDateGreaterThanEqual(
      String foodId, ReservationStatus status, LocalDate date);

  @Query(
      "SELECT r.foodId as foodId, COUNT(r) as orderCount "
          + "FROM FoodReservation r "
          + "WHERE r.status = :status "
          + "AND r.reservationDate BETWEEN :startDate AND :endDate "
          + "GROUP BY r.foodId")
  List<Object[]> getOrderStatistics(
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("status") ReservationStatus status);

  boolean existsByPersonIdAndReservationDateAndStatus(
      String personId, LocalDate reservationDate, ReservationStatus status);
}