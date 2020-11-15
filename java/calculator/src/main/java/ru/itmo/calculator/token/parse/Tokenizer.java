package ru.itmo.calculator.token.parse;

import ru.itmo.calculator.token.Token;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tokenizer {
    private static final Set<Character> operators = new HashSet<>();
    private static final Set<Character> braces = new HashSet<>();

    static {
        operators.add('+');
        operators.add('-');
        operators.add('*');
        operators.add('/');
        braces.add('(');
        braces.add(')');
    }

    private final List<Token> tokens = new ArrayList<>();

    private State state = new EmptyState();

    public void handle(final char c) {
        state.handle(c, this);
    }

    public List<Token> getResult() {
        if (!(state instanceof EmptyState)) {
            state.handle(' ', this);
        }
        return tokens;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public static Set<Character> getOperators() {
        return operators;
    }

    public static Set<Character> getBraces() {
        return braces;
    }

    protected List<Token> getTokens() {
        return tokens;
    }
}
