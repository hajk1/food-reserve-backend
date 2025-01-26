package me.hajk1.foodreservation.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.UUID;
import me.hajk1.foodreservation.dto.DailyMenuResponse;
import me.hajk1.foodreservation.dto.FoodRequest;
import me.hajk1.foodreservation.dto.FoodReservationRequest;
import me.hajk1.foodreservation.dto.FoodResponse;
import me.hajk1.foodreservation.exception.ResourceNotFoundException;
import me.hajk1.foodreservation.mapper.DailyMenuMapper;
import me.hajk1.foodreservation.mapper.FoodMapper;
import me.hajk1.foodreservation.model.DailyMenu;
import me.hajk1.foodreservation.model.Food;
import me.hajk1.foodreservation.repository.DailyMenuRepository;
import me.hajk1.foodreservation.repository.FoodRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FoodService {
    private final FoodRepository foodRepository;
  private final DailyMenuRepository dailyMenuRepository;
    private final FoodMapper foodMapper;
  private final DailyMenuMapper dailyMenuMapper;

  public FoodService(
      FoodRepository foodRepository,
      DailyMenuRepository dailyMenuRepository,
      FoodMapper foodMapper,
      DailyMenuMapper dailyMenuMapper) {
        this.foodRepository = foodRepository;
    this.dailyMenuRepository = dailyMenuRepository;
        this.foodMapper = foodMapper;
    this.dailyMenuMapper = dailyMenuMapper;
    }

    public List<FoodResponse> getAvailableFoods(LocalDate date) {
    return dailyMenuRepository.findByDateAndRemainingServingsGreaterThan(date, 0).stream()
        .map(
            dailyMenu -> {
              FoodResponse response = foodMapper.toResponse(dailyMenu.getFood());
              response.setRemainingServings(dailyMenu.getRemainingServings());
              return response;
            })
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
  public void addFoodToDaily(Long foodId, LocalDate date, int maxServings)
      throws ResourceNotFoundException {
    Food food =
        foodRepository
            .findById(foodId)
            .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

    DailyMenu dailyMenu =
        DailyMenu.builder()
            .food(food)
            .date(date)
            .maxServings(maxServings)
            .remainingServings(maxServings)
            .build();

    dailyMenuRepository.save(dailyMenu);
  }

  public DailyMenuResponse addFoodToDaily(@Valid FoodReservationRequest request)
      throws ResourceNotFoundException {
    Food food =
        foodRepository
            .findById(Long.valueOf(request.getFoodId()))
            .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

    DailyMenu dailyMenu =
        DailyMenu.builder()
            .food(food)
            .date(request.getReservationDate())
            .maxServings(request.getMaxServings())
            .remainingServings(request.getMaxServings())
            .build();

    return dailyMenuMapper.toResponse(dailyMenuRepository.save(dailyMenu));
  }

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
}