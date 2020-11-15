package ru.itmo.calculator;

import org.junit.jupiter.api.Test;
import ru.itmo.calculator.exception.InvalidExpressionException;
import ru.itmo.calculator.exception.InvalidOperatorException;
import ru.itmo.calculator.exception.InvalidParityException;
import ru.itmo.calculator.exception.InvalidTokenException;
import ru.itmo.calculator.token.parse.Tokenizer;
import ru.itmo.calculator.visitor.CalcVisitor;
import ru.itmo.calculator.visitor.ParserVisitor;
import ru.itmo.calculator.visitor.PrintVisitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    public Long testCalculator(final String input) {
        final var tokenizer = new Tokenizer();
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

        final var calcVisitor = new CalcVisitor();
        for (final var t : rpnTokens) {
            t.accept(calcVisitor);
        }

        return calcVisitor.getResult();
    }

    @Test
    public void testCalculatorNoFailure() {
        assertEquals(2L, testCalculator("2"));
        assertEquals(4L, testCalculator("2 + 2"));
        assertEquals(4L, testCalculator("(2 + 2)"));
        assertEquals(4L, testCalculator("( (2 + 2))"));
        assertEquals(4L, testCalculator("2+2"));
        assertEquals(4L, testCalculator("2 +2"));
        assertEquals(4L, testCalculator("2+ 2"));
        assertEquals(0L, testCalculator("( (2 - 2))"));
        assertEquals(-1L, testCalculator("( (1 - 2  ))"));
        assertEquals(6L, testCalculator("2 + 2 * 2"));
        assertEquals(8L, testCalculator("(2 + 2) * 2"));
        assertEquals(6L, testCalculator("2 * 3"));
        assertEquals(4L, testCalculator("16 /4"));
        assertEquals(3L, testCalculator("14 /4"));
        assertEquals(0L, testCalculator("2/5"));
        assertEquals(0L, testCalculator("0/1"));
        assertEquals(23L, testCalculator("21-5*5/2+(44/4-12/3)*2"));
        assertEquals(33L, testCalculator("1*2*3*4+5+6+7-8-9/2/3"));
    }

    @Test
    public void testCalculatorInvalidExpression() {
        assertThrows(InvalidExpressionException.class, () -> testCalculator(""));
        assertThrows(InvalidExpressionException.class, () -> testCalculator("()"));
        assertThrows(InvalidExpressionException.class, () -> testCalculator("()()()()()()"));
        assertThrows(InvalidExpressionException.class, () -> testCalculator("2 2"));
        assertThrows(InvalidExpressionException.class, () -> testCalculator("((2 2 3 "));
    }

    @Test
    public void testCalculatorInvalidOperation() {
        assertThrows(InvalidOperatorException.class, () -> testCalculator("2 +"));
        assertThrows(InvalidOperatorException.class, () -> testCalculator("2 *(2 + )"));
        assertThrows(InvalidOperatorException.class, () -> testCalculator("-2"));
    }

    @Test
    public void testCalculatorInvalidParity() {
        assertThrows(InvalidParityException.class, () -> testCalculator("2+2)"));
        assertThrows(InvalidParityException.class, () -> testCalculator("(2+2))"));
        assertThrows(InvalidParityException.class, () -> testCalculator("1*2+(2-1))"));
    }

    @Test
    public void testCalculatorInvalidToken() {
        assertThrows(InvalidTokenException.class, () -> testCalculator("2."));
        assertThrows(InvalidTokenException.class, () -> testCalculator("2 + a"));
        assertThrows(InvalidTokenException.class, () -> testCalculator("(2 = 2)"));
        assertThrows(InvalidTokenException.class, () -> testCalculator("( ([2 + 2]))"));
        assertThrows(InvalidTokenException.class, () -> testCalculator(","));
    }

}
