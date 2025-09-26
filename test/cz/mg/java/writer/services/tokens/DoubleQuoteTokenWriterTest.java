package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.test.LineAssert;
import cz.mg.token.test.TokenFactory;

import static cz.mg.test.Assert.assertEquals;

public @Test class DoubleQuoteTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + DoubleQuoteTokenWriterTest.class.getSimpleName() + " ... ");

        DoubleQuoteTokenWriterTest test = new DoubleQuoteTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteSimple();
        test.testWriteSpecialCharacters();
        test.testWriteLines();

        System.out.println("OK");
    }

    private final @Service DoubleQuoteTokenWriter writer = DoubleQuoteTokenWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(t.doubleQuote(""));
        assertEquals("\"\"", result);
    }

    private void testWriteSimple() {
        String result = writer.write(t.doubleQuote("foo"));
        assertEquals("\"foo\"", result);
    }

    private void testWriteSpecialCharacters() {
        String result = writer.write(t.doubleQuote("\\foo\nbar\rfoo\"bar\n\rbarfoo\r\n-\b-\t"));
        assertEquals("\"\\\\foo\\nbar\\rfoo\\\"bar\\n\\rbarfoo\\r\\n-\\b-\\t\"", result);
    }

    private void testWriteLines() {
        LineAssert.assertEquals(
            new List<>("\"foo\""),
            writer.writeLines(t.doubleQuote("foo"))
        );
    }
}
