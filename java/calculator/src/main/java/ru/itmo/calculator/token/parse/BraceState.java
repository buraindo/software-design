package ru.itmo.calculator.token.parse;

import ru.itmo.calculator.token.Brace;

public class BraceState implements State {
    private final char brace;

    public BraceState(final char c) {
        this.brace = c;
    }

    @Override
    public void handle(final char c, final Tokenizer tokenizer) {
        tokenizer.getTokens().add(new Brace(brace));
        tokenizer.setState(getNewState(c));
    }
}
