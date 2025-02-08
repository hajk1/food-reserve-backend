package me.hajk1.foodreservation.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import me.hajk1.foodreservation.dto.DailyMenuRequest;
import me.hajk1.foodreservation.dto.DailyMenuResponse;
import me.hajk1.foodreservation.dto.FoodRequest;
import me.hajk1.foodreservation.dto.FoodResponse;
import me.hajk1.foodreservation.exception.ActiveOrdersExistException;
import me.hajk1.foodreservation.exception.ResourceNotFoundException;
import me.hajk1.foodreservation.mapper.DailyMenuMapper;
import me.hajk1.foodreservation.mapper.FoodMapper;
import me.hajk1.foodreservation.model.DailyMenu;
import me.hajk1.foodreservation.model.Food;
import me.hajk1.foodreservation.model.enums.ReservationStatus;
import me.hajk1.foodreservation.repository.DailyMenuRepository;
import me.hajk1.foodreservation.repository.FoodRepository;
import me.hajk1.foodreservation.repository.ReservationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FoodService {
    private final FoodRepository foodRepository;
  private final DailyMenuRepository dailyMenuRepository;
    private final FoodMapper foodMapper;
  private final DailyMenuMapper dailyMenuMapper;
  private final ReservationRepository reservationRepository;

  public FoodService(
      FoodRepository foodRepository,
      DailyMenuRepository dailyMenuRepository,
      FoodMapper foodMapper,
      DailyMenuMapper dailyMenuMapper,
      ReservationRepository reservationRepository) {
        this.foodRepository = foodRepository;
    this.dailyMenuRepository = dailyMenuRepository;
        this.foodMapper = foodMapper;
    this.dailyMenuMapper = dailyMenuMapper;
    this.reservationRepository = reservationRepository;
  }

  @PreAuthorize("hasRole('ADMIN')")
  public List<FoodResponse> getAllFoods() {
    return foodRepository.findAll().stream()
        .map(foodMapper::toResponse)
        .collect(Collectors.toList());
  }

  // Modified method to get available foods for a specific date
  public List<FoodResponse> getAvailableFoodsByDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date parameter is required");
    }

    return dailyMenuRepository.findByDateAndRemainingServingsGreaterThan(date, 0).stream()
        .map(foodMapper::toResponseWithDailyMenu)
        .collect(Collectors.toList());
  }

  @Transactional
  public void updateRemainingServings(Long foodId, LocalDate date)
      throws ResourceNotFoundException {
    DailyMenu dailyMenu =
        dailyMenuRepository
            .findByFoodIdAndDate(foodId, date)
            .orElseThrow(() -> new ResourceNotFoundException("Food not available for this date"));

    if (dailyMenu.getRemainingServings() <= 0) {
      throw new IllegalStateException("No more servings available");
    }

    dailyMenu.setRemainingServings(dailyMenu.getRemainingServings() - 1);
    dailyMenuRepository.save(dailyMenu);
    }

  @PreAuthorize("hasRole('ADMIN')")
  public DailyMenuResponse addFoodToDaily(@Valid DailyMenuRequest request)
      throws ResourceNotFoundException {
    Food food =
        foodRepository
            .findById(request.getFoodId())
            .orElseThrow(() -> new ResourceNotFoundException("Food", "id", request.getFoodId()));

    DailyMenu dailyMenu =
        DailyMenu.builder()
            .food(food)
            .date(request.getDate())
            .maxServings(request.getMaxServings())
            .remainingServings(request.getMaxServings())
            .build();

    return dailyMenuMapper.toResponse(dailyMenuRepository.save(dailyMenu));
  }

  @PreAuthorize("hasRole('ADMIN')")
  public FoodResponse addFood(FoodRequest request) {
    Food food =
        Food.builder()
            .name(request.getName())
            .description(request.getDescription())
            .category(request.getCategory())
            .available(true)
            .build();

    return foodMapper.toResponse(foodRepository.save(food));
  }

  @PreAuthorize("hasRole('ADMIN')")
  public FoodResponse updateFood(String id, FoodRequest request) throws ResourceNotFoundException {
    Food food =
        foodRepository
            .findById(Long.valueOf(id))
            .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

    food.setName(request.getName());
    food.setDescription(request.getDescription());
    food.setCategory(request.getCategory());
    food.setAvailable(request.isAvailable());

    return foodMapper.toResponse(foodRepository.save(food));
  }

  @Transactional
  public void restoreRemainingServings(Long foodId, LocalDate date)
      throws ResourceNotFoundException {
    DailyMenu dailyMenu =
        dailyMenuRepository
            .findByFoodIdAndDate(foodId, date)
            .orElseThrow(() -> new ResourceNotFoundException("Food not available for this date"));

    if (dailyMenu.getRemainingServings() < dailyMenu.getMaxServings()) {
      dailyMenu.setRemainingServings(dailyMenu.getRemainingServings() + 1);
      dailyMenuRepository.save(dailyMenu);
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void deleteDailyMenu(Long id) {
    DailyMenu dailyMenu =
        dailyMenuRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Daily menu not found with id: " + id));

    // Check for active orders from today onwards
    boolean hasActiveOrders =
        reservationRepository.existsByFoodIdAndStatusAndReservationDateGreaterThanEqual(
            dailyMenu.getFood().getId().toString(), ReservationStatus.CONFIRMED, LocalDate.now());

    if (hasActiveOrders) {
      throw new ActiveOrdersExistException(
          "Cannot delete menu item as there are active orders for this food.");
    }

    dailyMenuRepository.deleteById(id);
  }
}