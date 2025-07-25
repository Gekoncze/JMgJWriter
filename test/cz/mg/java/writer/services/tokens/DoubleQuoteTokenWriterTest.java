package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.token.tokens.quote.DoubleQuoteToken;

import static cz.mg.test.Assert.assertEquals;

public @Test class DoubleQuoteTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + DoubleQuoteTokenWriterTest.class.getSimpleName() + " ... ");

        DoubleQuoteTokenWriterTest test = new DoubleQuoteTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteSimple();
        test.testWriteSpecialCharacters();

        System.out.println("OK");
    }

    private final @Service DoubleQuoteTokenWriter writer = DoubleQuoteTokenWriter.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(new DoubleQuoteToken("", -1));
        assertEquals("\"\"", result);
    }

    private void testWriteSimple() {
        String result = writer.write(new DoubleQuoteToken("foo", -1));
        assertEquals("\"foo\"", result);
    }

    private void testWriteSpecialCharacters() {
        String result = writer.write(new DoubleQuoteToken("\\foo\nbar\rfoo\"bar\n\rbarfoo\r\n-\b-\t", -1));
        assertEquals("\"\\\\foo\\nbar\\rfoo\\\"bar\\n\\rbarfoo\\r\\n-\\b-\\t\"", result);
    }
}
