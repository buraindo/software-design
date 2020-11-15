package ru.itmo.calculator.visitor;

import ru.itmo.calculator.exception.InvalidExpressionException;
import ru.itmo.calculator.exception.InvalidOperatorException;
import ru.itmo.calculator.token.Brace;
import ru.itmo.calculator.token.NumberToken;
import ru.itmo.calculator.token.Operation;
import ru.itmo.calculator.token.Token;

import java.util.ArrayDeque;
import java.util.Deque;

public class CalcVisitor implements TokenVisitor {
    private final Deque<Token> tokens = new ArrayDeque<>();

    @Override
    public void visit(final NumberToken token) {
        tokens.push(token);
    }

    @Override
    public void visit(final Brace token) {
        // no action
    }

    @Override
    public void visit(final Operation token) {
        if (tokens.size() < 2) {
            throw new InvalidOperatorException(token.getOperator());
        }
        final var left = tokens.pop();
        final var right = tokens.pop();
        if (!(left instanceof NumberToken)) {
            throw new InvalidOperatorException(((Operation) left).getOperator());
        }
        if (!(right instanceof NumberToken)) {
            throw new InvalidOperatorException(((Operation) right).getOperator());
        }
        final var l = ((NumberToken) left).getNumber();
        final var r = ((NumberToken) right).getNumber();
        switch (token.getOperator()) {
            case '+':
                tokens.push(new NumberToken(l + r));
                break;
            case '-':
                tokens.push(new NumberToken(l - r));
                break;
            case '*':
                tokens.push(new NumberToken(l * r));
                break;
            case '/':
                tokens.push(new NumberToken(l / r));
                break;
        }
    }

    public Long getResult() {
        if (tokens.size() != 1) {
            throw new InvalidExpressionException();
        }
        return ((NumberToken) tokens.peek()).getNumber();
    }
}
