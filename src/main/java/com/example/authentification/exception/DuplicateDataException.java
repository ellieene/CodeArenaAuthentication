package com.example.authentification.exception;

/**
 * Исключение, для обозначения повторения логина
 */
public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
