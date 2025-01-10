package com.example.foodreservation.controller;

import com.example.foodreservation.dto.FoodReservationRequest;
import com.example.foodreservation.dto.FoodReservationResponse;
import com.example.foodreservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservations", description = "Reservation management APIs")
public class ReservationController {
    
    private final ReservationService reservationService;
    
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new reservation")
    public FoodReservationResponse createReservation(@Valid @RequestBody FoodReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @GetMapping("/person/{personId}")
    @Operation(summary = "Get reservations for a person with optional date range filter")
    public List<FoodReservationResponse> getPersonReservations(
            @PathVariable String personId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return reservationService.getPersonReservations(personId, startDate, endDate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an ordered food by ID")
    public void deleteOrderedFood(@PathVariable String id) {
        reservationService.deleteOrderedFood(id);
    }
}