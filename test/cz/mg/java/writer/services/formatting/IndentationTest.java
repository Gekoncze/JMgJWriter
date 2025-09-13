package cz.mg.java.writer.services.formatting;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.test.LineAssert;
import cz.mg.test.Assertions;

public @Test class IndentationTest {
    public static void main(String[] args) {
        System.out.print("Running " + IndentationTest.class.getSimpleName() + " ... ");

        IndentationTest test = new IndentationTest();
        test.testIndentEmptyLine();
        test.testIndentLine();
        test.testIndentLinesEmpty();
        test.testIndentLinesSingle();
        test.testIndentLinesMultiple();
        test.testIndentLinesSingleBlank();
        test.testIndentLinesMultipleWithBlank();

        System.out.println("OK");
    }

    private final @Service Indentation indentation = Indentation.getInstance();

    private void testIndentEmptyLine() {
        Assertions.assertThat(indentation.indent(""))
            .withMessage("Unexpected result of indentation.")
            .isEqualTo("");
    }

    private void testIndentLine() {
        Assertions.assertThat(indentation.indent("foo"))
            .withMessage("Unexpected result of indentation.")
            .isEqualTo("    foo");
    }

    private void testIndentLinesEmpty() {
        LineAssert.assertEquals(
            new List<>(),
            indentation.indent(new List<>())
        );
    }

    private void testIndentLinesSingle() {
        LineAssert.assertEquals(
            new List<>("    foo bar"),
            indentation.indent(new List<>("foo bar"))
        );
    }

    private void testIndentLinesMultiple() {
        List<String> input = new List<>(
            "foo bar",
            "moo",
            "bark"
        );

        LineAssert.assertEquals(
            new List<>(
                "    foo bar",
                "    moo",
                "    bark"
            ),
            indentation.indent(input)
        );
    }

    private void testIndentLinesSingleBlank() {
        List<String> input = new List<>("  \t");

        LineAssert.assertEquals(
            new List<>(""),
            indentation.indent(input)
        );
    }

    private void testIndentLinesMultipleWithBlank() {
        List<String> input = new List<>(
            "foo bar",
            "moo",
            "    "
        );

        LineAssert.assertEquals(
            new List<>(
                "    foo bar",
                "    moo",
                ""
            ),
            indentation.indent(input)
        );
    }
}
