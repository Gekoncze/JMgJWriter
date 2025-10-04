package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.code.formatter.test.LineAssert;
import cz.mg.collections.list.List;
import cz.mg.token.tokens.NumberToken;

import static cz.mg.test.Assert.assertEquals;

public @Test class NumberTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + NumberTokenWriterTest.class.getSimpleName() + " ... ");

        NumberTokenWriterTest test = new NumberTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteNumber();
        test.testWriteLines();

        System.out.println("OK");
    }

    private final @Service NumberTokenWriter writer = NumberTokenWriter.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(new NumberToken("", -1));
        assertEquals("", result);
    }

    private void testWriteNumber() {
        String result = writer.write(new NumberToken("3.14f", -1));
        assertEquals("3.14f", result);
    }

    private void testWriteLines() {
        LineAssert.assertEquals(
            new List<>("3.14f"),
            writer.writeLines(new NumberToken("3.14f", -1))
        );
    }
}
