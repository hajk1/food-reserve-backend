package me.hajk1.foodreservation.validation;

import me.hajk1.foodreservation.util.DateUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FutureDateValidator implements ConstraintValidator<FutureDate, LocalDate> {
    
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        return DateUtils.isValidFutureDate(date);
    }
}