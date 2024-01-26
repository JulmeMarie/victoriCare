package com.victoricare.api.validators;

import java.lang.annotation.Target;

import com.victoricare.api.validators.impl.NameValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Constraint(validatedBy = NameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "Invalid Name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
