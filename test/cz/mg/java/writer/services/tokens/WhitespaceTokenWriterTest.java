package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.token.tokens.WhitespaceToken;

import static cz.mg.test.Assert.assertEquals;

public @Test class WhitespaceTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + WhitespaceTokenWriterTest.class.getSimpleName() + " ... ");

        WhitespaceTokenWriterTest test = new WhitespaceTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteWhitespaces();
        test.testWriteNewLines();

        System.out.println("OK");
    }

    private final @Service WhitespaceTokenWriter writer = WhitespaceTokenWriter.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(new WhitespaceToken("", -1));
        assertEquals("", result);
    }

    private void testWriteWhitespaces() {
        String result = writer.write(new WhitespaceToken("    ", -1));
        assertEquals("    ", result);
    }

    private void testWriteNewLines() {
        String result = writer.write(new WhitespaceToken("\n\r\r\n \n \r", -1));
        assertEquals("      ", result);
    }
}
