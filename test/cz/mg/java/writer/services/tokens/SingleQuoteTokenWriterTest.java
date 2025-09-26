package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.test.LineAssert;
import cz.mg.token.test.TokenFactory;

import static cz.mg.test.Assert.assertEquals;

public @Test class SingleQuoteTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + SingleQuoteTokenWriterTest.class.getSimpleName() + " ... ");

        SingleQuoteTokenWriterTest test = new SingleQuoteTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteSimple();
        test.testWriteSpecialCharacters();
        test.testWriteLines();

        System.out.println("OK");
    }

    private final @Service SingleQuoteTokenWriter writer = SingleQuoteTokenWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(t.singleQuote(""));
        assertEquals("''", result);
    }

    private void testWriteSimple() {
        String result = writer.write(t.singleQuote("a"));
        assertEquals("'a'", result);
    }

    private void testWriteSpecialCharacters() {
        String result = writer.write(t.singleQuote("\\foo\nbar\rfoo'bar\n\rbarfoo\r\n-\b-\t"));
        assertEquals("'\\\\foo\\nbar\\rfoo\\'bar\\n\\rbarfoo\\r\\n-\\b-\\t'", result);
    }

    private void testWriteLines() {
        LineAssert.assertEquals(
            new List<>("'a'"),
            writer.writeLines(t.singleQuote("a"))
        );
    }
}
