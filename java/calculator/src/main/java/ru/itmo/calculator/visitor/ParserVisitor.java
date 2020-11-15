package ru.itmo.calculator.visitor;

import ru.itmo.calculator.exception.InvalidParityException;
import ru.itmo.calculator.token.Brace;
import ru.itmo.calculator.token.NumberToken;
import ru.itmo.calculator.token.Operation;
import ru.itmo.calculator.token.Token;

import java.util.*;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> tokens = new ArrayList<>();
    private final Deque<Token> operations = new ArrayDeque<>();

    private static final Map<Character, Integer> priorities = new HashMap<>();

    static {
        priorities.put('+', 1);
        priorities.put('-', 1);
        priorities.put('*', 2);
        priorities.put('/', 2);
    }

    @Override
    public void visit(final NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(final Brace token) {
        if (token.getBrace() == '(') {
            operations.push(token);
        } else {
            while (true) {
                if (operations.isEmpty()) {
                    throw new InvalidParityException();
                }
                final var current = operations.pop();
                if (current instanceof Brace && ((Brace) current).getBrace() == '(') {
                    break;
                } else {
                    tokens.add(current);
                }
            }
        }
    }

    @Override
    public void visit(final Operation token) {
        while (true) {
            final var peek = operations.peek();
            if (peek instanceof Operation) {
                final var operator = ((Operation) peek).getOperator();
                if (priorities.get(operator) >= priorities.get(token.getOperator())) {
                    tokens.add(operations.pop());
                } else break;
            } else break;
        }
        operations.push(token);
    }

    public List<Token> getResult() {
        while (!operations.isEmpty()) {
            tokens.add(operations.pop());
        }
        return tokens;
    }
}
