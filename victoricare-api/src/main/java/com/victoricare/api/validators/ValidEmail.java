package com.victoricare.api.validators;

import java.lang.annotation.Target;

import com.victoricare.api.validators.impl.EmailValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Constraint(validatedBy = EmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "Invalid E-mail";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
