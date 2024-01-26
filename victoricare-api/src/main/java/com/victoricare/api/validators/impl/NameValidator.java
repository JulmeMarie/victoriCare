package com.victoricare.api.validators.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.victoricare.api.enums.EPattern;
import com.victoricare.api.validators.ValidName;
import com.victoricare.api.validators.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        log.info("Checking for name.");

        if (!Validator.checkNonNullableString(EPattern.NAME, value)) {
            log.info("Name not valid.");
            return false;
        }
        return true;
    }
}
