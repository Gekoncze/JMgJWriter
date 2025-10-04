package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assertions;
import cz.mg.token.test.TokenFactory;

import static cz.mg.code.formatter.test.LineAssert.assertEquals;

public @Test class BlockQuoteTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + BlockQuoteTokenWriterTest.class.getSimpleName() + " ... ");

        BlockQuoteTokenWriterTest test = new BlockQuoteTokenWriterTest();
        test.testWriteSingleLineUnsupported();
        test.testWriteLinesEmpty();
        test.testWriteLinesSimple();
        test.testWriteLines();
        test.testWriteLinesWithTabs();
        test.testWriteLinesWithSpecialCharacters();

        System.out.println("OK");
    }

    private final @Service BlockQuoteTokenWriter writer = BlockQuoteTokenWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testWriteSingleLineUnsupported() {
        Assertions.assertThatCode(() -> writer.write(t.blockQuote("foo")))
            .withMessage("It is not possible to construct block quote token on a single line.")
            .throwsException(WriterException.class);
    }

    private void testWriteLinesEmpty() {
        assertEquals(
            new List<>(
                "\"\"\"",
                "\"\"\""
            ),
            writer.writeLines(t.blockQuote(""))
        );
    }

    private void testWriteLinesSimple() {
        assertEquals(
            new List<>(
                "\"\"\"",
                "foo\"\"\""
            ),
            writer.writeLines(t.blockQuote("foo"))
        );
    }

    private void testWriteLines() {
        assertEquals(
            new List<>(
                "\"\"\"",
                "",
                "foo",
                "bar",
                "foobar",
                "\"\"\""
            ),
            writer.writeLines(t.blockQuote("\nfoo\nbar\nfoobar\n"))
        );
    }

    private void testWriteLinesWithTabs() {
        assertEquals(
            new List<>(
                "\"\"\"",
                "\t\tfoo\tbar\"\"\""
            ),
            writer.writeLines(t.blockQuote("\t\tfoo\tbar"))
        );
    }

    private void testWriteLinesWithSpecialCharacters() {
        assertEquals(
            new List<>(
                "\"\"\"",
                "\\\\foo",
                "bar\\rfoo\\\"bar",
                "\\rbarfoo\\r",
                "-\\b-\t\"\"\""
            ),
            writer.writeLines(t.blockQuote("\\foo\nbar\rfoo\"bar\n\rbarfoo\r\n-\b-\t"))
        );
    }
}
