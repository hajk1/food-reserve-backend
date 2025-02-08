package me.hajk1.foodreservation.exception;

public class DuplicateReservationException extends RuntimeException {
  public DuplicateReservationException(String message) {
    super(message);
  }
}
