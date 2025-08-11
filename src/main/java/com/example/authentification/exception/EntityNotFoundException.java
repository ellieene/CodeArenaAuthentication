package com.example.authentification.exception;

/**
 * Исключение, для обозначения повторения логина
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
