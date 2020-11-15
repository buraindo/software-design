package ru.itmo.calculator.token.parse;

import ru.itmo.calculator.token.NumberToken;

public class NumberState implements State {
    private final StringBuilder numberBuilder = new StringBuilder();

    public NumberState(final char c) {
        numberBuilder.append(c);
    }

    @Override
    public void handle(final char c, final Tokenizer tokenizer) {
        if (Character.isDigit(c)) {
            numberBuilder.append(c);
        } else {
            tokenizer.getTokens().add(new NumberToken(Long.parseLong(numberBuilder.toString())));
            tokenizer.setState(getNewState(c));
        }
    }
}
