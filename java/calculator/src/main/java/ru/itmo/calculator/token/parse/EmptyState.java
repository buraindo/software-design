package ru.itmo.calculator.token.parse;

public class EmptyState implements State {
    @Override
    public void handle(final char c, final Tokenizer tokenizer) {
        tokenizer.setState(getNewState(c));
    }
}
