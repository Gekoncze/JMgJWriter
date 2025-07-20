package cz.mg.java.writer.services.token.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

public @Test class SquareBracketsWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + SquareBracketsWriterTest.class.getSimpleName() + " ... ");

        SquareBracketsWriterTest test = new SquareBracketsWriterTest();
        test.testWriteEmpty();
        test.testWrite();
        test.testWriteNested();

        System.out.println("OK");
    }

    private final @Service SquareBracketsWriter writer = SquareBracketsWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteEmpty() {
        Assert.assertEquals("[]", writer.write(b.squareBrackets()));
    }

    private void testWrite() {
        Assert.assertEquals("[i]", writer.write(b.squareBrackets(t.word("i"))));
    }

    private void testWriteNested() {
        Assert.assertEquals("[[1]b]", writer.write(
            b.squareBrackets(
                b.squareBrackets(
                    t.number("1")
                ),
                t.word("b")
            )
        ));
    }
}
