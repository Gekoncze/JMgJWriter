package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.test.QuickAssert;
import cz.mg.token.tokens.WordToken;

import static cz.mg.test.Assert.assertEquals;

public @Test class WordTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + WordTokenWriterTest.class.getSimpleName() + " ... ");

        WordTokenWriterTest test = new WordTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteName();
        test.testWriteLines();

        System.out.println("OK");
    }

    private final @Service WordTokenWriter writer = WordTokenWriter.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(new WordToken("", -1));
        assertEquals("", result);
    }

    private void testWriteName() {
        String result = writer.write(new WordToken("foobar", -1));
        assertEquals("foobar", result);
    }

    private void testWriteLines() {
        QuickAssert.compare(
            new List<>("foobar"),
            writer.writeLines(new WordToken("foobar", -1))
        );
    }
}
