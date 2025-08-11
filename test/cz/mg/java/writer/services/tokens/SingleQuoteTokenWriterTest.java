package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.token.tokens.quotes.SingleQuoteToken;

import static cz.mg.test.Assert.assertEquals;

public @Test class SingleQuoteTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + SingleQuoteTokenWriterTest.class.getSimpleName() + " ... ");

        SingleQuoteTokenWriterTest test = new SingleQuoteTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteSimple();
        test.testWriteSpecialCharacters();

        System.out.println("OK");
    }

    private final @Service SingleQuoteTokenWriter writer = SingleQuoteTokenWriter.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(new SingleQuoteToken("", -1));
        assertEquals("''", result);
    }

    private void testWriteSimple() {
        String result = writer.write(new SingleQuoteToken("a", -1));
        assertEquals("'a'", result);
    }

    private void testWriteSpecialCharacters() {
        String result = writer.write(new SingleQuoteToken("\\foo\nbar\rfoo'bar\n\rbarfoo\r\n-\b-\t", -1));
        assertEquals("'\\\\foo\\nbar\\rfoo\\'bar\\n\\rbarfoo\\r\\n-\\b-\\t'", result);
    }
}
