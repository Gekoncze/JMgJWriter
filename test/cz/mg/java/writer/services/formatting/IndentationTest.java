package cz.mg.java.writer.services.formatting;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
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
        List<String> input = new List<>();

        List<String> expectations = new List<>();

        List<String> reality = indentation.indent(input);

        Assertions.assertThatCollection(reality)
            .withMessage("Unexpected result of indentation.")
            .isEqualTo(expectations);
    }

    private void testIndentLinesSingle() {
        List<String> input = new List<>("foo bar");

        List<String> expectations = new List<>("    foo bar");

        List<String> reality = indentation.indent(input);

        Assertions.assertThatCollection(reality)
            .withMessage("Unexpected result of indentation.")
            .isEqualTo(expectations);
    }

    private void testIndentLinesMultiple() {
        List<String> input = new List<>(
            "foo bar",
            "moo",
            "bark"
        );

        List<String> expectations = new List<>(
            "    foo bar",
            "    moo",
            "    bark"
        );

        List<String> reality = indentation.indent(input);

        Assertions.assertThatCollection(reality)
            .withMessage("Unexpected result of indentation.")
            .isEqualTo(expectations);
    }

    private void testIndentLinesSingleBlank() {
        List<String> input = new List<>("  \t");

        List<String> expectations = new List<>("");

        List<String> reality = indentation.indent(input);

        Assertions.assertThatCollection(reality)
            .withMessage("Unexpected result of indentation.")
            .isEqualTo(expectations);
    }

    private void testIndentLinesMultipleWithBlank() {
        List<String> input = new List<>(
            "foo bar",
            "moo",
            "    "
        );

        List<String> expectations = new List<>(
            "    foo bar",
            "    moo",
            ""
        );

        List<String> reality = indentation.indent(input);

        Assertions.assertThatCollection(reality)
            .withMessage("Unexpected result of indentation.")
            .isEqualTo(expectations);
    }
}
