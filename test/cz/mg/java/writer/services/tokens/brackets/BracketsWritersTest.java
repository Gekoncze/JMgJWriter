package cz.mg.java.writer.services.tokens.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assert;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;
import cz.mg.token.tokens.brackets.Brackets;

public @Test class BracketsWritersTest {
    public static void main(String[] args) {
        System.out.print("Running " + BracketsWritersTest.class.getSimpleName() + " ... ");

        BracketsWritersTest test = new BracketsWritersTest();
        test.testWriteUnsupported();
        test.testWriteRound();
        test.testWriteSquare();
        test.testWriteCurly();

        System.out.println("OK");
    }

    private final @Service BracketsWriters writers = BracketsWriters.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteUnsupported() {
        Assert.assertThatCode(() -> writers.write(new Brackets()))
            .withMessage("Writer exception should be thrown for unsupported brackets.")
            .throwsException(WriterException.class);
    }

    private void testWriteRound() {
        Assert.assertEquals("(1)", writers.write(b.roundBrackets(t.number("1"))));
    }

    private void testWriteSquare() {
        Assert.assertEquals("[1]", writers.write(b.squareBrackets(t.number("1"))));
    }

    private void testWriteCurly() {
        Assert.assertEquals("{1}", writers.write(b.curlyBrackets(t.number("1"))));
    }
}
