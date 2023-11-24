package com.victoricare.api.validators;

import java.lang.annotation.Target;
import com.victoricare.api.validators.impl.PageValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE })
@Constraint(validatedBy = PageValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPage {
    String message() default "Invalid PageDTO";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
