package cz.mg.java.writer.components;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

public @Test class BlockBuilderTest {
    public static void main(String[] args) {
        System.out.print("Running " + BlockBuilderTest.class.getSimpleName() + " ... ");

        BlockBuilderTest test = new BlockBuilderTest();
        test.testNoInput();
        test.testNoGroupedLines();
        test.testOneStandaloneLine();
        test.testTwoStandaloneLines();
        test.testThreeStandaloneLines();
        test.testOneEmptyStandaloneLine();
        test.testTwoEmptyStandaloneLines();
        test.testThreeEmptyStandaloneLines();
        test.testOneGroupedLine();
        test.testTwoGroupedLines();
        test.testThreeGroupedLines();
        test.testOneEmptyGroupedLine();
        test.testTwoEmptyGroupedLines();
        test.testThreeEmptyGroupedLines();
        test.testThreeGroupedLinesWithEmptyLine();
        test.testCombined();

        System.out.println("OK");
    }

    private void testNoInput() {
        List<String> result = new BlockBuilder().build();

        List<String> expectations = new List<>();

        Assert.assertThatCollections(expectations, result)
            .withMessage("Result should be empty for no input.")
            .areEqual();
    }

    private void testNoGroupedLines() {
        List<String> result = new BlockBuilder()
            .addLines(new List<>())
            .build();

        List<String> expectations = new List<>();

        Assert.assertThatCollections(expectations, result)
            .withMessage("Result should be empty for empty input.")
            .areEqual();
    }

    private void testOneStandaloneLine() {
        List<String> result = new BlockBuilder()
            .addLine("foo.bar.foobar")
            .build();

        List<String> expectations = new List<>("foo.bar.foobar");

        Assert.assertThatCollections(expectations, result)
            .withMessage("Result should contain given line.")
            .areEqual();
    }

    private void testTwoStandaloneLines() {
        List<String> result = new BlockBuilder()
            .addLine("foo.bar.foobar")
            .addLine("cz.mg.java")
            .build();

        List<String> expectations = new List<>(
            "foo.bar.foobar",
            "",
            "cz.mg.java"
        );

        Assert.assertThatCollections(expectations, result)
            .withMessage("Two standalone lines should be separated by an empty line.")
            .areEqual();
    }

    private void testThreeStandaloneLines() {
        List<String> result = new BlockBuilder()
            .addLine("foo.bar.foobar")
            .addLine("seven.eight.nine")
            .addLine("terra.incognita")
            .build();

        List<String> expectations = new List<>(
            "foo.bar.foobar",
            "",
            "seven.eight.nine",
            "",
            "terra.incognita"
        );

        Assert.assertThatCollections(expectations, result)
            .withMessage("Three standalone lines should be separated by empty lines.")
            .areEqual();
    }

    private void testOneEmptyStandaloneLine() {
        List<String> result = new BlockBuilder()
            .addLine("")
            .build();

        List<String> expectations = new List<>("");

        Assert.assertThatCollections(expectations, result)
            .withMessage("Given empty lines should be treated like any other line.")
            .areEqual();
    }

    private void testTwoEmptyStandaloneLines() {
        List<String> result = new BlockBuilder()
            .addLine("")
            .addLine("")
            .build();

        List<String> expectations = new List<>("", "", "");

        Assert.assertThatCollections(expectations, result)
            .withMessage("Given empty lines should be treated like any other line.")
            .areEqual();
    }

    private void testThreeEmptyStandaloneLines() {
        List<String> result = new BlockBuilder()
            .addLine("")
            .addLine("")
            .addLine("")
            .build();

        List<String> expectations = new List<>("", "", "", "", "");

        Assert.assertThatCollections(expectations, result)
            .withMessage("Given empty lines should be treated like any other line.")
            .areEqual();
    }

    private void testOneGroupedLine() {
        List<String> result = new BlockBuilder()
            .addLines(new List<>("foo.bar.foobar"))
            .build();

        List<String> expectations = new List<>("foo.bar.foobar");

        Assert.assertThatCollections(expectations, result)
            .withMessage("Result should contain given line.")
            .areEqual();
    }

    private void testTwoGroupedLines() {
        List<String> result = new BlockBuilder()
            .addLines(new List<>(
                "foo.bar.foobar",
                "cz.mg.java"
            ))
            .build();

        List<String> expectations = new List<>(
            "foo.bar.foobar",
            "cz.mg.java"
        );

        Assert.assertThatCollections(expectations, result)
            .withMessage("Two grouped lines should not be separated.")
            .areEqual();
    }

    private void testThreeGroupedLines() {
        List<String> result = new BlockBuilder()
            .addLines(new List<>(
                "foo.bar.foobar",
                "seven.eight.nine",
                "terra.incognita"
            ))
            .build();

        List<String> expectations = new List<>(
            "foo.bar.foobar",
            "seven.eight.nine",
            "terra.incognita"
        );

        Assert.assertThatCollections(expectations, result)
            .withMessage("Three grouped lines should not be separated.")
            .areEqual();
    }

    private void testOneEmptyGroupedLine() {
        List<String> result = new BlockBuilder()
            .addLines(new List<>(""))
            .build();

        List<String> expectations = new List<>("");

        Assert.assertThatCollections(expectations, result)
            .withMessage("Given empty lines should be treated like any other lines.")
            .areEqual();
    }

    private void testTwoEmptyGroupedLines() {
        List<String> result = new BlockBuilder()
            .addLines(new List<>(
                "",
                ""
            ))
            .build();

        List<String> expectations = new List<>(
            "",
            ""
        );

        Assert.assertThatCollections(expectations, result)
            .withMessage("Given empty lines should be treated like any other lines.")
            .areEqual();
    }

    private void testThreeEmptyGroupedLines() {
        List<String> result = new BlockBuilder()
            .addLines(new List<>(
                "",
                "",
                ""
            ))
            .build();

        List<String> expectations = new List<>(
            "",
            "",
            ""
        );

        Assert.assertThatCollections(expectations, result)
            .withMessage("Given empty lines should be treated like any other lines.")
            .areEqual();
    }

    private void testThreeGroupedLinesWithEmptyLine() {
        List<String> result = new BlockBuilder()
            .addLines(new List<>(
                "foo.bar.foobar",
                "seven.eight.nine",
                "",
                "terra.incognita"
            ))
            .build();

        List<String> expectations = new List<>(
            "foo.bar.foobar",
            "seven.eight.nine",
            "",
            "terra.incognita"
        );

        Assert.assertThatCollections(expectations, result)
            .withMessage("Given empty lines should be treated like any other line.")
            .areEqual();
    }

    private void testCombined() {
        List<String> result = new BlockBuilder()
            .addLines(new List<>(
                "foo.bar.foobar",
                "seven.eight.nine"
            ))
            .addLine("terra.incognita")
            .build();

        List<String> expectations = new List<>(
            "foo.bar.foobar",
            "seven.eight.nine",
            "",
            "terra.incognita"
        );

        Assert.assertThatCollections(expectations, result)
            .withMessage("Standalone line should be separated from grouped lines with an empty line.")
            .areEqual();
    }
}
