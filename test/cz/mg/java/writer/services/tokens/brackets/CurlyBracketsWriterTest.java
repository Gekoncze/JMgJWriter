package cz.mg.java.writer.services.tokens.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

public @Test class CurlyBracketsWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + CurlyBracketsWriterTest.class.getSimpleName() + " ... ");

        CurlyBracketsWriterTest test = new CurlyBracketsWriterTest();
        test.testWriteEmpty();
        test.testWrite();
        test.testWriteNested();

        System.out.println("OK");
    }

    private final @Service CurlyBracketsWriter writer = CurlyBracketsWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteEmpty() {
        Assert.assertEquals("{}", writer.write(b.curlyBrackets()));
    }

    private void testWrite() {
        Assert.assertEquals("{ return 3; }", writer.write(b.curlyBrackets(
            t.whitespace(" "),
            t.word("return"),
            t.whitespace(" "),
            t.number("3"),
            t.symbol(";"),
            t.whitespace(" ")
        )));
    }

    private void testWriteNested() {
        Assert.assertEquals("{class Bar{}}", writer.write(
            b.curlyBrackets(
                t.word("class"),
                t.whitespace(" "),
                t.word("Bar"),
                b.curlyBrackets()
            )
        ));
    }
}
