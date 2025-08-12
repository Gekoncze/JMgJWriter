package cz.mg.java.writer.services.formatting;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

public @Test class IndentationTest {
    public static void main(String[] args) {
        System.out.print("Running " + IndentationTest.class.getSimpleName() + " ... ");

        IndentationTest test = new IndentationTest();
        test.testEmpty();
        test.testSingle();
        test.testMultiple();
        test.testSingleBlank();
        test.testMultipleWithBlank();

        System.out.println("OK");
    }

    private final @Service Indentation indentation = Indentation.getInstance();

    private void testEmpty() {
        List<String> input = new List<>();

        List<String> expectations = new List<>();

        List<String> reality = indentation.add(input);

        Assert.assertThatCollections(expectations, reality)
            .withMessage("Unexpected result of indentation.")
            .areEqual();
    }

    private void testSingle() {
        List<String> input = new List<>("foo bar");

        List<String> expectations = new List<>("    foo bar");

        List<String> reality = indentation.add(input);

        Assert.assertThatCollections(expectations, reality)
            .withMessage("Unexpected result of indentation.")
            .areEqual();
    }

    private void testMultiple() {
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

        List<String> reality = indentation.add(input);

        Assert.assertThatCollections(expectations, reality)
            .withMessage("Unexpected result of indentation.")
            .areEqual();
    }

    private void testSingleBlank() {
        List<String> input = new List<>("  \t");

        List<String> expectations = new List<>("");

        List<String> reality = indentation.add(input);

        Assert.assertThatCollections(expectations, reality)
            .withMessage("Unexpected result of indentation.")
            .areEqual();
    }

    private void testMultipleWithBlank() {
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

        List<String> reality = indentation.add(input);

        Assert.assertThatCollections(expectations, reality)
            .withMessage("Unexpected result of indentation.")
            .areEqual();
    }
}
