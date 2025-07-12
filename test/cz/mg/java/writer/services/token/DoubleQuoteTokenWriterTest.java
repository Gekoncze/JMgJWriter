package cz.mg.java.writer.services.token;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.token.tokens.quote.DoubleQuoteToken;

import static cz.mg.test.Assert.assertEquals;
import static cz.mg.test.Assert.assertThatCode;

public @Test class DoubleQuoteTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + DoubleQuoteTokenWriterTest.class.getSimpleName() + " ... ");

        DoubleQuoteTokenWriterTest test = new DoubleQuoteTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteSimple();
        test.testWriteMultiLine();
        test.testWriteTerminatingSequence();

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

    private void testWriteMultiLine() {
        String result = writer.write(new DoubleQuoteToken("foo\nbar\rfoobar\n\rbarfoo\r\n", -1));
        assertEquals("\"foo bar foobar barfoo \"", result);
    }

    private void testWriteTerminatingSequence() {
        assertThatCode(() -> writer.write(new DoubleQuoteToken("\"", -1)))
            .withMessage("Terminating character should not be written.")
            .throwsException(WriterException.class);
    }
}
