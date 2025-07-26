package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.token.tokens.SymbolToken;

import static cz.mg.test.Assert.assertEquals;

public @Test class SymbolTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + SymbolTokenWriterTest.class.getSimpleName() + " ... ");

        SymbolTokenWriterTest test = new SymbolTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteSymbols();

        System.out.println("OK");
    }

    private final @Service SymbolTokenWriter writer = SymbolTokenWriter.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(new SymbolToken("", -1));
        assertEquals("", result);
    }

    private void testWriteSymbols() {
        String result = writer.write(new SymbolToken("+=", -1));
        assertEquals("+=", result);
    }
}
