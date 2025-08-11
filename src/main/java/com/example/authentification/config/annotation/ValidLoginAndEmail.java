package com.example.authentification.config.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * Annotation to the username/email
 */
@Documented
@Constraint(validatedBy = ValidLoginAndEmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLoginAndEmail {

    String message() default "Некорректный login/email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

