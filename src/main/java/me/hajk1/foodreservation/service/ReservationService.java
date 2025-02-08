package me.hajk1.foodreservation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import me.hajk1.foodreservation.dto.FoodReservationRequest;
import me.hajk1.foodreservation.dto.FoodReservationResponse;
import me.hajk1.foodreservation.exception.DuplicateReservationException;
import me.hajk1.foodreservation.exception.ResourceNotFoundException;
import me.hajk1.foodreservation.mapper.ReservationMapper;
import me.hajk1.foodreservation.model.FoodReservation;
import me.hajk1.foodreservation.model.enums.ReservationStatus;
import me.hajk1.foodreservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
  private final FoodService foodService;

  @Autowired
  public ReservationService(
      ReservationRepository reservationRepository,
      ReservationMapper reservationMapper,
      FoodService foodService) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    this.foodService = foodService;
  }

  public FoodReservationResponse createReservation(FoodReservationRequest request)
      throws ResourceNotFoundException {
    // Check if user already has a confirmed reservation for this date
    boolean hasExistingReservation =
        reservationRepository.existsByPersonIdAndReservationDateAndStatus(
            request.getPersonId(), request.getReservationDate(), ReservationStatus.CONFIRMED);

    if (hasExistingReservation) {
      throw new DuplicateReservationException(
          "You already have a confirmed food reservation for " + request.getReservationDate());
    }
    // Check if food is available and update remaining servings
    foodService.updateRemainingServings(
        Long.valueOf(request.getFoodId()), request.getReservationDate());

        FoodReservation reservation = FoodReservation.builder()
            .id(UUID.randomUUID().toString())
            .foodId(request.getFoodId())
            .personId(request.getPersonId())
            .reservationDate(request.getReservationDate())
            .status(ReservationStatus.CONFIRMED)
            .build();

        FoodReservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toResponse(savedReservation);
    }

  public List<FoodReservationResponse> getPersonReservations(
      String personId, LocalDate startDate, LocalDate endDate) {
    List<FoodReservation> reservations;
    if (startDate != null && endDate != null) {
      reservations =
          reservationRepository.findByPersonIdAndReservationDateBetweenAndStatus(
              personId, startDate, endDate, ReservationStatus.CONFIRMED);
    } else if (startDate != null) {
      reservations =
          reservationRepository.findByPersonIdAndReservationDateGreaterThanEqualAndStatus(
              personId, startDate, ReservationStatus.CONFIRMED);
    } else if (endDate != null) {
      reservations =
          reservationRepository.findByPersonIdAndReservationDateLessThanEqualAndStatus(
              personId, endDate, ReservationStatus.CONFIRMED);
    } else {
      reservations =
          reservationRepository.findByPersonIdAndStatus(personId, ReservationStatus.CONFIRMED);
    }

    return reservations.stream().map(reservationMapper::toResponse).collect(Collectors.toList());
    }

  public void cancelReservation(String id) throws ResourceNotFoundException {
    FoodReservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

    // Increment remaining servings in daily menu
    foodService.restoreRemainingServings(
        Long.valueOf(reservation.getFoodId()), reservation.getReservationDate());

    reservation.setStatus(ReservationStatus.CANCELLED);
    reservationRepository.save(reservation);
  }
}