package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JInitializer;
import cz.mg.java.writer.test.LineAssert;
import cz.mg.token.test.TokenFactory;

public @Test class JInitializerWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JInitializerWriterTest.class.getSimpleName() + " ... ");

        JInitializerWriterTest test = new JInitializerWriterTest();
        test.testWriteEmpty();
        test.testWriteSingleLine();
        test.testWriteMultipleLines();

        System.out.println("OK");
    }

    private final @Service JInitializerWriter writer = JInitializerWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testWriteEmpty() {
        JInitializer initializer = new JInitializer();

        LineAssert.assertEquals(
            new List<>(
                "static {",
                "}"
            ),
            writer.write(initializer)
        );
    }

    private void testWriteSingleLine() {
        JInitializer initializer = new JInitializer();
        initializer.setImplementation(new List<>(
            t.word("FooBar"),
            t.symbol("."),
            t.word("i"),
            t.whitespace(" "),
            t.symbol("="),
            t.whitespace(" "),
            t.number("5"),
            t.symbol(";")
        ));

        LineAssert.assertEquals(
            new List<>(
                "static {",
                "    FooBar.i = 5;",
                "}"
            ),
            writer.write(initializer)
        );
    }

    private void testWriteMultipleLines() {
        JInitializer initializer = new JInitializer();
        initializer.setImplementation(new List<>(
            t.singleLineComment(" must be set early"),
            t.whitespace("\n"),
            t.word("FooBar"),
            t.symbol("."),
            t.word("i"),
            t.whitespace(" "),
            t.symbol("="),
            t.whitespace(" "),
            t.number("5"),
            t.symbol(";")
        ));

        LineAssert.assertEquals(
            new List<>(
                "static {",
                "    // must be set early",
                "    FooBar.i = 5;",
                "}"
            ),
            writer.write(initializer)
        );
    }
}
