package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.code.formatter.test.LineAssert;
import cz.mg.collections.list.List;

public @Test class LineSeparatorTest {
    public static void main(String[] args) {
        System.out.print("Running " + LineSeparatorTest.class.getSimpleName() + " ... ");

        LineSeparatorTest test = new LineSeparatorTest();
        test.testSplitEmpty();
        test.testSplitAlone();
        test.testSplitNone();
        test.testSplitBegin();
        test.testSplitMiddle();
        test.testSplitEnd();
        test.testSplitMultiple();

        System.out.println("OK");
    }

    private final @Service LineSeparator separator = LineSeparator.getInstance();

    private void testSplitEmpty() {
        LineAssert.assertEquals(
            new List<>(""),
            separator.split("")
        );
    }

    private void testSplitAlone() {
        LineAssert.assertEquals(
            new List<>(
                "",
                ""
            ),
            separator.split("\n")
        );
    }

    private void testSplitNone() {
        LineAssert.assertEquals(
            new List<>("foobar"),
            separator.split("foobar")
        );
    }

    private void testSplitBegin() {
        LineAssert.assertEquals(
            new List<>(
                "",
                "foobar"
            ),
            separator.split("\nfoobar")
        );
    }

    private void testSplitMiddle() {
        LineAssert.assertEquals(
            new List<>(
                "foo",
                "bar"
            ),
            separator.split("foo\nbar")
        );
    }

    private void testSplitEnd() {
        LineAssert.assertEquals(
            new List<>(
                "foobar",
                ""
            ),
            separator.split("foobar\n")
        );
    }

    private void testSplitMultiple() {
        LineAssert.assertEquals(
            new List<>(
                "",
                "foo",
                "bar",
                "",
                "foobar",
                "barfoo",
                ""
            ),
            separator.split("\nfoo\nbar\n\nfoobar\nbarfoo\n")
        );
    }
}
