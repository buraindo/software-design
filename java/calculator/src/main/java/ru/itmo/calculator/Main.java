package ru.itmo.calculator;

import ru.itmo.calculator.token.parse.Tokenizer;
import ru.itmo.calculator.visitor.CalcVisitor;
import ru.itmo.calculator.visitor.ParserVisitor;
import ru.itmo.calculator.visitor.PrintVisitor;

public class Main {

    public static void main(final String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <expression>");
            return;
        }
        final var tokenizer = new Tokenizer();
        final var input = args[0];
        for (final var c : input.toCharArray()) {
            tokenizer.handle(c);
        }

        final var tokens = tokenizer.getResult();
        final var parserVisitor = new ParserVisitor();
        for (final var t : tokens) {
            t.accept(parserVisitor);
        }

        final var rpnTokens = parserVisitor.getResult();
        final var printVisitor = new PrintVisitor();
        for (final var t : rpnTokens) {
            t.accept(printVisitor);
        }
        System.out.println(printVisitor.getResult());

        final var calcVisitor = new CalcVisitor();
        for (final var t : rpnTokens) {
            t.accept(calcVisitor);
        }
        System.out.println(calcVisitor.getResult());
    }

}
