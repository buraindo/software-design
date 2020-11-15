package ru.itmo.calculator.exception;

public class InvalidOperatorException extends RuntimeException {
    public InvalidOperatorException(final char operator) {
        super(String.format("Expected operand, but got '%c'", operator));
    }
}
