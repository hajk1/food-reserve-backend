package me.hajk1.foodreservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import me.hajk1.foodreservation.model.DailyMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMenuRepository extends JpaRepository<DailyMenu, Long> {
  List<DailyMenu> findByDateAndRemainingServingsGreaterThan(LocalDate date, int minServings);

  Optional<DailyMenu> findByFoodIdAndDate(Long foodId, LocalDate date);

  void deleteById(Long id);
}
