package com.example.CodeArena.exception;

/**
 * Исключение, для обозначения повторения логина
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
