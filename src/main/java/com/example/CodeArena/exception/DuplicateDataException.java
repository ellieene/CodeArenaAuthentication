package com.example.CodeArena.exception;

/**
 * Исключение, для обозначения повторения логина
 */
public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
