package cz.mg.java.writer.services.tokens.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

public @Test class RoundBracketsWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + RoundBracketsWriterTest.class.getSimpleName() + " ... ");

        RoundBracketsWriterTest test = new RoundBracketsWriterTest();
        test.testWriteEmpty();
        test.testWrite();
        test.testWriteNested();

        System.out.println("OK");
    }

    private final @Service RoundBracketsWriter writer = RoundBracketsWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteEmpty() {
        Assert.assertEquals("()", writer.write(b.roundBrackets()));
    }

    private void testWrite() {
        Assert.assertEquals("(a+2*3.14)", writer.write(b.roundBrackets(
            t.word("a"),
            t.symbol("+"),
            t.number("2"),
            t.symbol("*"),
            t.number("3.14")
        )));
    }

    private void testWriteNested() {
        Assert.assertEquals("(1*(2+3))", writer.write(
            b.roundBrackets(
                t.number("1"),
                t.symbol("*"),
                b.roundBrackets(
                    t.number("2"),
                    t.symbol("+"),
                    t.number("3")
                )
            )
        ));
    }
}
