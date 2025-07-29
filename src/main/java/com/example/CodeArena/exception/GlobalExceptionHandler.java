package com.example.CodeArena.exception;

import com.example.CodeArena.model.responce.StringResponse;
import io.jsonwebtoken.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.example.CodeArena.util.CommonStrings.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleException(EntityNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateDataException.class)
    public String handleException(DuplicateDataException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String field = propertyPath.contains(".")
                    ? propertyPath.substring(propertyPath.lastIndexOf('.') + 1)
                    : propertyPath;

            errors.put(field, violation.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }



//    // Ошибки JWT токена
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(ExpiredJwtException.class)
//    public StringResponse handleException(ExpiredJwtException e) {
//        return new StringResponse(EXPIRED_JWT_EXCEPTION);
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(UnsupportedJwtException.class)
//    public StringResponse handleException(UnsupportedJwtException e) {
//        return new StringResponse(UNSUPPORTED_JWT_EXCEPTION);
//    }
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(MalformedJwtException.class)
//    public StringResponse handleException(MalformedJwtException e) {
//        return new StringResponse(MALFORMED_JWT_EXCEPTION);
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(SignatureException.class)
//    public StringResponse handleException(SignatureException e) {
//        return new StringResponse(e.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public StringResponse handleIllegalArgumentException(IllegalArgumentException e) {
//        return new StringResponse(ILLEGAL_ARGUMENT_EXCEPTION);
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(JwtException.class)
//    public StringResponse handleException(JwtException e) {
//        return new StringResponse(JWT_EXCEPTION);
//    }
}