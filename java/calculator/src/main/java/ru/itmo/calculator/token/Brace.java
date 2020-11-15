package ru.itmo.calculator.token;

import ru.itmo.calculator.visitor.TokenVisitor;

public class Brace implements Token {
    private final char brace;

    public Brace(final char c) {
        this.brace = c;
    }

    public void accept(final TokenVisitor visitor) {
        visitor.visit(this);
    }

    public char getBrace() {
        return brace;
    }
}
