package cz.mg.java.writer.services.token;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.token.test.TokenFactory;

public @Test class ExpressionWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + ExpressionWriterTest.class.getSimpleName() + " ... ");

        ExpressionWriterTest test = new ExpressionWriterTest();
        test.testWriteEmpty();
        test.testWriteSingle();
        test.testWriteSimple();

        System.out.println("OK");
    }

    private final @Service ExpressionWriter writer = ExpressionWriter.getInstance();
    private final @Service TokenFactory f = TokenFactory.getInstance();

    private void testWriteEmpty() {
        Assert.assertEquals("", writer.write(new List<>()));
    }

    private void testWriteSingle() {
        Assert.assertEquals("Hewwo", writer.write(new List<>(f.word("Hewwo"))));
    }

    private void testWriteSimple() {
        Assert.assertEquals(
            "return 1 + 2 + 'a' /* testing */",
            writer.write(new List<>(
                f.word("return"),
                f.whitespace(" "),
                f.number("1"),
                f.whitespace(" "),
                f.symbol("+"),
                f.whitespace(" "),
                f.number("2"),
                f.whitespace(" "),
                f.symbol("+"),
                f.whitespace(" "),
                f.singleQuote("a"),
                f.whitespace(" "),
                f.comment(" testing ")
            ))
        );
    }
}
