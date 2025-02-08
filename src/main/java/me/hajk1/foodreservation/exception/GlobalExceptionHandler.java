package me.hajk1.foodreservation.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, WebRequest request) {
    log.error("Failed to read HTTP message", ex);
    ErrorResponse errorResponse =
        new ErrorResponse(
            "Invalid request body. Please check the request format and try again.",
            "INVALID_REQUEST_BODY",
            System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    log.error("Resource not found", ex);
    ErrorResponse errorResponse =
        new ErrorResponse(ex.getMessage(), "RESOURCE_NOT_FOUND", System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    log.error("Validation failed", ex);
        Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
              log.debug("Validation error - field: {}, message: {}", fieldName, errorMessage);
            });

    ErrorResponse errorResponse =
        new ErrorResponse(
            "Validation failed: " + errors, "VALIDATION_FAILED", System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleAllUncaughtException(
      Exception ex, WebRequest request) {
    log.error("Uncaught exception occurred", ex);
    ErrorResponse errorResponse =
        new ErrorResponse(
            "An unexpected error occurred: " + ex.getMessage(),
            "INTERNAL_SERVER_ERROR",
            System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  @ExceptionHandler(DuplicateReservationException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateReservationException(
      DuplicateReservationException ex) {
    ErrorResponse error =
        new ErrorResponse(
            HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), System.currentTimeMillis());
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
    ErrorResponse error =
        new ErrorResponse(
            HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage(), System.currentTimeMillis());
    return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
  }
}