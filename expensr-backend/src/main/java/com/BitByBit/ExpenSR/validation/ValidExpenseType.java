package com.BitByBit.ExpenSR.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExpenseTypeValidator.class)
@Documented
public @interface ValidExpenseType {
    String message() default "Invalid expense type. Must be one of: BUSINESS, PERSONAL, TRAVEL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
