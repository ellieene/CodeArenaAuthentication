package com.example.CodeArena.config.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

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

