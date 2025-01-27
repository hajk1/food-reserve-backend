package me.hajk1.foodreservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import me.hajk1.foodreservation.dto.FoodReservationRequest;
import me.hajk1.foodreservation.dto.FoodReservationResponse;
import me.hajk1.foodreservation.exception.ResourceNotFoundException;
import me.hajk1.foodreservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservations", description = "Reservation management APIs")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a new reservation")
  public ResponseEntity<FoodReservationResponse> createReservation(
      @Valid @RequestBody FoodReservationRequest request) {
    log.debug("Creating reservation with request: {}", request);
    return ResponseEntity.ok(reservationService.createReservation(request));
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
  @Operation(summary = "Cancel a reservation")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiResponse(responseCode = "204", description = "Reservation cancelled successfully")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> cancelReservation(@PathVariable String id)
      throws ResourceNotFoundException {
    reservationService.cancelReservation(id);
    return ResponseEntity.noContent().build();
    }
}