package me.hajk1.foodreservation.service;

import me.hajk1.foodreservation.dto.FoodResponse;
import me.hajk1.foodreservation.mapper.FoodMapper;
import me.hajk1.foodreservation.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    
    @Autowired
    public FoodService(FoodRepository foodRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
    }
    
    public List<FoodResponse> getAvailableFoods(LocalDate date) {
        return foodRepository.findAvailableFoodsByDate(date)
            .stream()
            .map(foodMapper::toResponse)
            .collect(Collectors.toList());
    }
}