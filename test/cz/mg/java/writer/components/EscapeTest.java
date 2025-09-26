package cz.mg.java.writer.components;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;

public @Test class EscapeTest {
    public static void main(String[] args) {
        System.out.print("Running " + EscapeTest.class.getSimpleName() + " ... ");

        EscapeTest test = new EscapeTest();
        test.testEscapeEmpty();
        test.testEscapeNothing();
        test.testEscapeBackspace();
        test.testEscapeBackslash();
        test.testEscapeCarriageReturn();
        test.testEscapeDoubleQuote();
        test.testEscapeNewline();
        test.testEscapeSingleQuote();
        test.testEscapeTab();
        test.testEscapeMultiple();

        System.out.println("OK");
    }

    private void testEscapeEmpty() {
        Assert.assertEquals("", new Escape("").get());
        Assert.assertEquals("", new Escape("").backslash().get());
    }

    private void testEscapeNothing() {
        Assert.assertEquals("\\", new Escape("\\").get());
        Assert.assertEquals("\n", new Escape("\n").get());
        Assert.assertEquals("foo", new Escape("foo").get());
        Assert.assertEquals("foo\nbar", new Escape("foo\nbar").get());
    }

    private void testEscapeBackspace() {
        Assert.assertEquals("\\b", new Escape("\b").backspace().get());
        Assert.assertEquals("foo\\b", new Escape("foo\b").backspace().get());
        Assert.assertEquals("\\bar", new Escape("\bar").backspace().get());
    }

    private void testEscapeBackslash() {
        Assert.assertEquals("\\\\", new Escape("\\").backslash().get());
        Assert.assertEquals("foo\\\\", new Escape("foo\\").backslash().get());
        Assert.assertEquals("\\\\foo", new Escape("\\foo").backslash().get());
    }

    private void testEscapeCarriageReturn() {
        Assert.assertEquals("\\r", new Escape("\r").carriageReturn().get());
        Assert.assertEquals("rawr\\r", new Escape("rawr\r").carriageReturn().get());
        Assert.assertEquals("\\rawr", new Escape("\rawr").carriageReturn().get());
    }

    private void testEscapeDoubleQuote() {
        Assert.assertEquals("\\\"", new Escape("\"").doubleQuote().get());
        Assert.assertEquals("qq\\\"", new Escape("qq\"").doubleQuote().get());
        Assert.assertEquals("\\\"qq", new Escape("\"qq").doubleQuote().get());
    }

    private void testEscapeNewline() {
        Assert.assertEquals("\\n", new Escape("\n").newline().get());
        Assert.assertEquals("\\nline", new Escape("\nline").newline().get());
        Assert.assertEquals("line\\n", new Escape("line\n").newline().get());
    }

    private void testEscapeSingleQuote() {
        Assert.assertEquals("\\'", new Escape("'").singleQuote().get());
        Assert.assertEquals("\\'uu", new Escape("'uu").singleQuote().get());
        Assert.assertEquals("uu\\'", new Escape("uu'").singleQuote().get());
    }

    private void testEscapeTab() {
        Assert.assertEquals("\\t", new Escape("\t").tab().get());
        Assert.assertEquals("\\tab", new Escape("\tab").tab().get());
        Assert.assertEquals("tab\\t", new Escape("tab\t").tab().get());
    }

    private void testEscapeMultiple() {
        Assert.assertEquals(
            "\\tFoo bar\rfor bag\\n\\b...",
            new Escape("\tFoo bar\rfor bag\n\b...")
                .tab()
                .newline()
                .backspace()
                .get()
        );
    }
}
