package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.code.formatter.test.LineAssert;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

public @Test class ExpressionWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + ExpressionWriterTest.class.getSimpleName() + " ... ");

        ExpressionWriterTest test = new ExpressionWriterTest();
        test.testWriteEmpty();
        test.testWriteSingle();
        test.testWriteSimple();
        test.testWriteBrackets();
        test.testWriteLinesEmpty();
        test.testWriteLinesSingle();
        test.testWriteLinesSimple();
        test.testWriteLinesBrackets();

        System.out.println("OK");
    }

    private final @Service ExpressionWriter writer = ExpressionWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteEmpty() {
        Assert.assertEquals("", writer.write(new List<>()));
    }

    private void testWriteSingle() {
        Assert.assertEquals("Hewwo", writer.write(new List<>(t.word("Hewwo"))));
    }

    private void testWriteSimple() {
        Assert.assertEquals(
            "return 1 + 2 + 'a' // testing",
            writer.write(new List<>(
                t.word("return"),
                t.whitespace(" "),
                t.number("1"),
                t.whitespace(" "),
                t.symbol("+"),
                t.whitespace(" "),
                t.number("2"),
                t.whitespace(" "),
                t.symbol("+"),
                t.whitespace(" "),
                t.singleQuote("a"),
                t.whitespace(" "),
                t.singleLineComment(" testing")
            ))
        );
    }

    private void testWriteBrackets() {
        Assert.assertEquals("()[]{}", writer.write(new List<>(
            b.roundBrackets(),
            b.squareBrackets(),
            b.curlyBrackets()
        )));
    }

    private void testWriteLinesEmpty() {
        LineAssert.assertEquals(
            new List<>(),
            writer.writeLines(new List<>())
        );
    }

    private void testWriteLinesSingle() {
        LineAssert.assertEquals(
            new List<>("Hewwo"),
            writer.writeLines(new List<>(t.word("Hewwo")))
        );
    }

    private void testWriteLinesSimple() {
        LineAssert.assertEquals(
            new List<>(
                "return",
                "    foo;"
            ),
            writer.writeLines(new List<>(
                t.word("return"),
                t.whitespace("\n"),
                t.whitespace("    "),
                t.word("foo"),
                t.symbol(";")
            ))
        );
    }

    private void testWriteLinesBrackets() {
        LineAssert.assertEquals(
            new List<>(
                "foo(",
                "    bar",
                ")"
            ),
            writer.writeLines(new List<>(
                t.word("foo"),
                b.roundBrackets(
                    t.whitespace("\n"),
                    t.whitespace("    "),
                    t.word("bar"),
                    t.whitespace("\n")
                )
            ))
        );
    }
}
