package me.hajk1.foodreservation.repository;

import me.hajk1.foodreservation.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
  @Query("SELECT f FROM Food f WHERE f.available = true")
  List<Food> findAvailableFoodsByDate(LocalDate date);
}