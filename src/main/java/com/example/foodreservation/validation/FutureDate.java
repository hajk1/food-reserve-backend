package com.example.foodreservation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateValidator.class)
@Documented
public @interface FutureDate {
    String message() default "Date must be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}