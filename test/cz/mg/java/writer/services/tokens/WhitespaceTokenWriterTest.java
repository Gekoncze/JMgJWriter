package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.token.test.TokenFactory;

import static cz.mg.test.Assert.assertEquals;

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
        assertEquals("", writer.write(t.whitespace("")));
    }

    private void testWriteSpaces() {
        assertEquals(" ", writer.write(t.whitespace(" ")));
        assertEquals("    ", writer.write(t.whitespace("    ")));
    }

    private void testWriteTabs() {
        assertEquals("\t", writer.write(t.whitespace("\t")));
        assertEquals("\t\t", writer.write(t.whitespace("\t\t")));
    }

    private void testWriteNewLines() {
        assertEquals(" ", writer.write(t.whitespace("\n")));
        assertEquals(" ", writer.write(t.whitespace("\r\n")));
        assertEquals(" ", writer.write(t.whitespace("\n\r")));
        assertEquals("   ", writer.write(t.whitespace("\n\n\n")));
    }

    private void testWriteOther() {
        assertEquals("", writer.write(t.whitespace("\r\bx")));
        assertEquals(" ", writer.write(t.whitespace("\r \bx")));
    }

    private void testWriteMixed() {
        assertEquals(" \t ", writer.write(t.whitespace("\s\t\n")));
    }

    private void testWriteEmptyLines() {
        compare(new List<>(""), writer.writeLines(t.whitespace("")));
    }

    private void testWriteSpaceLines() {
        compare(new List<>(" "), writer.writeLines(t.whitespace(" ")));
        compare(new List<>("    "), writer.writeLines(t.whitespace("    ")));
    }

    private void testWriteTabLines() {
        compare(new List<>("\t"), writer.writeLines(t.whitespace("\t")));
        compare(new List<>("\t\t"), writer.writeLines(t.whitespace("\t\t")));
    }

    private void testWriteNewLineLines() {
        compare(new List<>("", ""), writer.writeLines(t.whitespace("\n")));
        compare(new List<>("", "", ""), writer.writeLines(t.whitespace("\n\n")));
    }

    private void testWriteOtherLines() {
        compare(new List<>(""), writer.writeLines(t.whitespace("\r\bx")));
        compare(new List<>(" "), writer.writeLines(t.whitespace("\r \bx")));
    }

    private void testWriteMixedLines() {
        compare(new List<>("    ", "\t", "", " "), writer.writeLines(t.whitespace("    \n\t\n\n ")));
    }

    private void compare(@Mandatory List<String> expectations, @Mandatory List<String> reality) {
        Assert.assertThatCollections(expectations, reality)
            .withMessage("Incorrect code generated.")
            .withPrintFunction(s -> '"' + s + '"')
            .areEqual();
    }
}
