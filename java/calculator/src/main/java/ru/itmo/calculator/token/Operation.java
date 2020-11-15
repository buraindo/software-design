package ru.itmo.calculator.token;

import ru.itmo.calculator.visitor.TokenVisitor;

public class Operation implements Token {
    private final char operator;

    public Operation(final char c) {
        this.operator = c;
    }

    public void accept(final TokenVisitor visitor) {
        visitor.visit(this);
    }

    public char getOperator() {
        return operator;
    }
}
