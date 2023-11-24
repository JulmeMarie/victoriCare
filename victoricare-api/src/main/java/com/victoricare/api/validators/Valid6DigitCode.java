package com.victoricare.api.validators;

import java.lang.annotation.Target;
import com.victoricare.api.validators.impl.Code6DigitValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Constraint(validatedBy = Code6DigitValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid6DigitCode {
    String message() default "Invalid Code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
