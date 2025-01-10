package com.example.foodreservation.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtils {
    
    private DateUtils() {
        // Utility class, prevent instantiation
    }
    
    public static LocalDateTime startOfDay(LocalDate date) {
        return date.atStartOfDay();
    }
    
    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }
    
    public static boolean isValidFutureDate(LocalDate date) {
        return date != null && !date.isBefore(LocalDate.now());
    }
}