package ru.itmo.calculator.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(final char c) {
        super(String.format("Unexpected token '%c'", c));
    }
}
