package me.hajk1.foodreservation.service;

import me.hajk1.foodreservation.dto.FoodReservationRequest;
import me.hajk1.foodreservation.dto.FoodReservationResponse;
import me.hajk1.foodreservation.mapper.ReservationMapper;
import me.hajk1.foodreservation.model.FoodReservation;
import me.hajk1.foodreservation.model.enums.ReservationStatus;
import me.hajk1.foodreservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

  @Autowired
  public ReservationService(
      ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public FoodReservationResponse createReservation(FoodReservationRequest request) {
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
          reservationRepository.findByPersonIdAndReservationDateBetween(
              personId, startDate, endDate);
    } else if (startDate != null) {
      reservations =
          reservationRepository.findByPersonIdAndReservationDateGreaterThanEqual(
              personId, startDate);
    } else if (endDate != null) {
      reservations =
          reservationRepository.findByPersonIdAndReservationDateLessThanEqual(personId, endDate);
    } else {
      reservations = reservationRepository.findByPersonId(personId);
    }

    return reservations.stream().map(reservationMapper::toResponse).collect(Collectors.toList());
    }

    public void deleteOrderedFood(String id) {
    reservationRepository.deleteById(id);
  }
}