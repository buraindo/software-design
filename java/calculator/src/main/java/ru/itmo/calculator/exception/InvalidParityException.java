package ru.itmo.calculator.exception;

public class InvalidParityException extends RuntimeException {
    public InvalidParityException() {
        super("Invalid parity: check amount of open and closing brackets in your expression");
    }
}
