package ru.itmo.calculator.token;

import ru.itmo.calculator.visitor.TokenVisitor;

public class NumberToken implements Token {
    private final Long number;

    public NumberToken(final Long number) {
        this.number = number;
    }

    @Override
    public void accept(final TokenVisitor visitor) {
        visitor.visit(this);
    }

    public Long getNumber() {
        return number;
    }
}
