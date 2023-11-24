package com.victoricare.api.validators;

import java.lang.annotation.Target;

import com.victoricare.api.validators.impl.PassValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Constraint(validatedBy = PassValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPass {
    String message() default "Invalid Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
