package me.hajk1.foodreservation.exception;

public class ActiveOrdersExistException extends RuntimeException {
  public ActiveOrdersExistException(String message) {
    super(message);
  }
}
