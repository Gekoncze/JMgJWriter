package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.java.writer.test.LineAssert;
import cz.mg.test.Assert;
import cz.mg.token.test.TokenFactory;

public @Test class WhitespaceTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + WhitespaceTokenWriterTest.class.getSimpleName() + " ... ");

        WhitespaceTokenWriterTest test = new WhitespaceTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteSpaces();
        test.testWriteTabs();
        test.testWriteNewLines();
        test.testWriteOther();
        test.testWriteMixed();
        test.testWriteEmptyLines();
        test.testWriteSpaceLines();
        test.testWriteTabLines();
        test.testWriteNewLineLines();
        test.testWriteOtherLines();
        test.testWriteMixedLines();

        System.out.println("OK");
    }

    private final @Service WhitespaceTokenWriter writer = WhitespaceTokenWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testWriteEmpty() {
        Assert.assertEquals("", writer.write(t.whitespace("")));
    }

    private void testWriteSpaces() {
        Assert.assertEquals(" ", writer.write(t.whitespace(" ")));
        Assert.assertEquals("    ", writer.write(t.whitespace("    ")));
    }

    private void testWriteTabs() {
        Assert.assertEquals("\t", writer.write(t.whitespace("\t")));
        Assert.assertEquals("\t\t", writer.write(t.whitespace("\t\t")));
    }

    private void testWriteNewLines() {
        Assert.assertEquals(" ", writer.write(t.whitespace("\n")));
        Assert.assertEquals("   ", writer.write(t.whitespace("\n\n\n")));
    }

    private void testWriteOther() {
        Assert.assertException(() -> writer.write(t.whitespace("\r")), WriterException.class);
        Assert.assertException(() -> writer.write(t.whitespace("\b")), WriterException.class);
        Assert.assertException(() -> writer.write(t.whitespace("x")), WriterException.class);
    }

    private void testWriteMixed() {
        Assert.assertEquals(" \t ", writer.write(t.whitespace("\s\t\n")));
    }

    private void testWriteEmptyLines() {
        LineAssert.assertEquals(new List<>(""), writer.writeLines(t.whitespace("")));
    }

    private void testWriteSpaceLines() {
        LineAssert.assertEquals(new List<>(" "), writer.writeLines(t.whitespace(" ")));
        LineAssert.assertEquals(new List<>("    "), writer.writeLines(t.whitespace("    ")));
    }

    private void testWriteTabLines() {
        LineAssert.assertEquals(new List<>("\t"), writer.writeLines(t.whitespace("\t")));
        LineAssert.assertEquals(new List<>("\t\t"), writer.writeLines(t.whitespace("\t\t")));
    }

    private void testWriteNewLineLines() {
        LineAssert.assertEquals(new List<>("", ""), writer.writeLines(t.whitespace("\n")));
        LineAssert.assertEquals(new List<>("", "", ""), writer.writeLines(t.whitespace("\n\n")));
    }

    private void testWriteOtherLines() {
        Assert.assertException(() -> writer.writeLines(t.whitespace("\r")), WriterException.class);
        Assert.assertException(() -> writer.writeLines(t.whitespace("\b")), WriterException.class);
        Assert.assertException(() -> writer.writeLines(t.whitespace("x")), WriterException.class);
    }

    private void testWriteMixedLines() {
        LineAssert.assertEquals(new List<>("    ", "\t", "", " "), writer.writeLines(t.whitespace("    \n\t\n\n ")));
    }
}
