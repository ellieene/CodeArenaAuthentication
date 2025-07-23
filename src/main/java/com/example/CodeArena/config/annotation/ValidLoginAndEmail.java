package com.example.CodeArena.config.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Size(min = 5, max = 255, message = "Строка должна содержать от 5 до 255 символов")
@NotBlank(message = "Строка не должна быть пустой")
public @interface ValidLoginAndEmail {
    String message() default "Некорректный login/email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
