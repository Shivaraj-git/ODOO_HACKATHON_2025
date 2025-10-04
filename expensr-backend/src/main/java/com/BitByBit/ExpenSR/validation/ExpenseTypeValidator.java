package com.BitByBit.ExpenSR.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ExpenseTypeValidator implements ConstraintValidator<ValidExpenseType, String> {

    private static final List<String> VALID_TYPES = Arrays.asList("BUSINESS", "PERSONAL", "TRAVEL");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation
        }
        return VALID_TYPES.contains(value.toUpperCase());
    }
}
