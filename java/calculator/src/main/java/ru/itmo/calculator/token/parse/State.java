package ru.itmo.calculator.token.parse;

import ru.itmo.calculator.exception.InvalidTokenException;

public interface State {
    void handle(char c, Tokenizer tokenizer);

    default State getNewState(final char c) {
        if (Tokenizer.getBraces().contains(c)) {
            return new BraceState(c);
        }
        if (Tokenizer.getOperators().contains(c)) {
            return new OperatorState(c);
        }
        if (Character.isDigit(c)) {
            return new NumberState(c);
        }
        if (Character.isWhitespace(c)) {
            return new EmptyState();
        }
        throw new InvalidTokenException(c);
    }
}
