package com.victoricare.api.validators;

import java.lang.annotation.Target;
import com.victoricare.api.validators.impl.TrueValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Constraint(validatedBy = TrueValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTrue {
    String message() default "Invalid true value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
