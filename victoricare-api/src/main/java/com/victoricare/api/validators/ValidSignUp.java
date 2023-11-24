package com.victoricare.api.validators;

import java.lang.annotation.Target;
import com.victoricare.api.validators.impl.SignUpValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE })
@Constraint(validatedBy = SignUpValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSignUp {
    String message() default "Invalid SignUpDTO";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
