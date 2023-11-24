package com.victoricare.api.validators.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.victoricare.api.validators.ValidTrue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrueValidator implements ConstraintValidator<ValidTrue, Boolean> {

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {

        log.info("Checking for boolean is true.");

        if (value == null || !value.booleanValue()) {
            log.info("Boolean is not true.");
            return false;
        }
        return true;
    }
}
