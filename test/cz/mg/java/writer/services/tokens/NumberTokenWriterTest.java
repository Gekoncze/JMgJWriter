package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.token.tokens.NumberToken;

import static cz.mg.test.Assert.assertEquals;

public @Test class NumberTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + NumberTokenWriterTest.class.getSimpleName() + " ... ");

        NumberTokenWriterTest test = new NumberTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteNumber();

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
}
