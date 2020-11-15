package ru.itmo.calculator.visitor;

import ru.itmo.calculator.token.Brace;
import ru.itmo.calculator.token.NumberToken;
import ru.itmo.calculator.token.Operation;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
}
