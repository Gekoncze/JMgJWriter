package cz.mg.java.writer.components;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.test.LineAssert;

public @Test class LineMergerTest {
    public static void main(String[] args) {
        System.out.print("Running " + LineMergerTest.class.getSimpleName() + " ... ");

        LineMergerTest test = new LineMergerTest();
        test.testMergeNone();
        test.testMergeEmpty();
        test.testMergeSingleLines();
        test.testMergeMultipleLines();
        test.testMergeMixedLines();
        test.testMergeFirstEmpty();
        test.testMergeLastEmpty();

        System.out.println("OK");
    }

    private void testMergeNone() {
        LineAssert.assertEquals(
            new List<>(),
            new LineMerger().get()
        );
    }

    private void testMergeEmpty() {
        LineAssert.assertEquals(
            new List<>(),
            new LineMerger()
                .merge(new List<>())
                .get()
        );

        LineAssert.assertEquals(
            new List<>(),
            new LineMerger()
                .merge(new List<>())
                .merge(new List<>())
                .get()
        );

        LineAssert.assertEquals(
            new List<>(),
            new LineMerger()
                .delimiter(" ")
                .merge(new List<>())
                .merge(new List<>())
                .merge(new List<>())
                .get()
        );
    }

    private void testMergeSingleLines() {
        LineAssert.assertEquals(
            new List<>("foo"),
            new LineMerger()
                .delimiter(" ")
                .merge(new List<>("foo"))
                .get()
        );

        LineAssert.assertEquals(
            new List<>("foobar"),
            new LineMerger()
                .merge(new List<>("foo"))
                .merge(new List<>("bar"))
                .get()
        );

        LineAssert.assertEquals(
            new List<>("a b-c"),
            new LineMerger()
                .merge(new List<>("a"))
                .delimiter(" ")
                .merge("b")
                .delimiter("-")
                .merge(new List<>("c"))
                .get()
        );
    }

    private void testMergeMultipleLines() {
        LineAssert.assertEquals(
            new List<>(
                "a",
                "foo",
                "b"
            ),
            new LineMerger()
                .merge(new List<>(
                    "a",
                    "foo",
                    "b"
                ))
                .get()
        );

        LineAssert.assertEquals(
            new List<>(
                "a",
                "foobar",
                "b"
            ),
            new LineMerger()
                .merge(new List<>(
                    "a",
                    "foo"
                ))
                .merge(new List<>(
                    "bar",
                    "b"
                ))
                .get()
        );

        LineAssert.assertEquals(
            new List<>(
                "a",
                "bc",
                "de",
                "f"
            ),
            new LineMerger()
                .merge(new List<>(
                    "a",
                    "b"
                ))
                .merge(new List<>(
                    "c",
                    "d"
                ))
                .merge(new List<>(
                    "e",
                    "f"
                ))
                .get()
        );
    }

    private void testMergeMixedLines() {
        LineAssert.assertEquals(
            new List<>(
                "a",
                "b c d",
                "e",
                "f"
            ),
            new LineMerger()
                .delimiter(" ")
                .merge(new List<>(
                    "a",
                    "b"
                ))
                .merge(new List<>(
                    "c"
                ))
                .merge(new List<>(
                    "d",
                    "e",
                    "f"
                ))
                .get()
        );
    }

    private void testMergeFirstEmpty() {
        LineAssert.assertEquals(
            new List<>(
                "foo",
                "bar"
            ),
            new LineMerger()
                .delimiter(" ")
                .merge(new List<>())
                .merge(new List<>(
                    "foo",
                    "bar"
                ))
                .get()
        );
    }

    private void testMergeLastEmpty() {
        LineAssert.assertEquals(
            new List<>(
                "foo",
                "bar"
            ),
            new LineMerger()
                .delimiter(" ")
                .merge(new List<>(
                    "foo",
                    "bar"
                ))
                .merge(new List<>())
                .get()
        );
    }
}
