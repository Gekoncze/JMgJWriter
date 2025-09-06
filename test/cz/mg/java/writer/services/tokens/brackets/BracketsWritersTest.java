package cz.mg.java.writer.services.tokens.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assertions;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;
import cz.mg.token.tokens.brackets.Brackets;

public @Test class BracketsWritersTest {
    public static void main(String[] args) {
        System.out.print("Running " + BracketsWritersTest.class.getSimpleName() + " ... ");

        BracketsWritersTest test = new BracketsWritersTest();
        test.testGetUnsupported();
        test.testGetRound();
        test.testGetSquare();
        test.testGetCurly();

        System.out.println("OK");
    }

    private final @Service BracketsWriters writers = BracketsWriters.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testGetUnsupported() {
        Assertions.assertThatCode(() -> writers.get(new Brackets()))
            .withMessage("Writer exception should be thrown for unsupported brackets.")
            .throwsException(WriterException.class);
    }

    private void testGetRound() {
        Assertions.assertThat(writers.get(b.roundBrackets(t.number("1"))))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(RoundBracketsWriter.class);
    }

    private void testGetSquare() {
        Assertions.assertThat(writers.get(b.squareBrackets(t.number("1"))))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(SquareBracketsWriter.class);
    }

    private void testGetCurly() {
        Assertions.assertThat(writers.get(b.curlyBrackets(t.number("1"))))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(CurlyBracketsWriter.class);
    }
}
