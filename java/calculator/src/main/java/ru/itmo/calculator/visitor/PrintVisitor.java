package ru.itmo.calculator.visitor;

import ru.itmo.calculator.token.Brace;
import ru.itmo.calculator.token.NumberToken;
import ru.itmo.calculator.token.Operation;

public class PrintVisitor implements TokenVisitor {
    private final StringBuilder builder = new StringBuilder();

    @Override
    public void visit(final NumberToken token) {
        builder.append(token.getNumber());
        builder.append(' ');
    }

    @Override
    public void visit(final Brace token) {
        builder.append(token.getBrace());
        builder.append(' ');
    }

    @Override
    public void visit(final Operation token) {
        builder.append(token.getOperator());
        builder.append(' ');
    }

    public String getResult() {
        return builder.toString();
    }
}
