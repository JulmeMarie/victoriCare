package com.victoricare.api.validators.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.victoricare.api.enums.EPattern;
import com.victoricare.api.validators.Valid6DigitCode;
import com.victoricare.api.validators.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Code6DigitValidator implements ConstraintValidator<Valid6DigitCode, Integer> {

    @Override
    public boolean isValid(Integer code, ConstraintValidatorContext context) {

        log.info("Checking for password.");
        if (!Validator.checkNonNullableInteger(EPattern.CODE_6DIGIT, code)) {
            log.info("6 Digit Code not valid.");
            return false;
        }
        return true;
    }
}
