package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JAnnotype;
import cz.mg.java.entities.JModifier;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.JVariable;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

import static cz.mg.java.writer.test.LineAssert.assertEquals;

public @Test class JAnnotypeWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JAnnotypeWriterTest.class.getSimpleName() + " ... ");

        JAnnotypeWriterTest test = new JAnnotypeWriterTest();
        test.testWriteSimple();
        test.testWriteSingleElement();
        test.testWriteSingleElementWithModifiers();
        test.testWriteSingleElementWithDefault();
        test.testWriteMultipleElements();
        test.testWriteComplex();

        System.out.println("OK");
    }

    private final @Service JAnnotypeWriter writer = JAnnotypeWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteSimple() {
        JAnnotype annotype = new JAnnotype();
        annotype.setName("FooBar");

        assertEquals(
            new List<>(
                "@interface FooBar {",
                "}"
            ),
            writer.write(annotype)
        );
    }

    private void testWriteSingleElement() {
        JAnnotype annotype = new JAnnotype();
        annotype.setName("FooBar");

        JVariable element = new JVariable();
        element.setType(new JType("int"));
        element.setName("foo");
        annotype.getElements().addLast(element);

        assertEquals(
            new List<>(
                "@interface FooBar {",
                "    int foo();",
                "}"
            ),
            writer.write(annotype)
        );
    }

    private void testWriteSingleElementWithModifiers() {
        JAnnotype annotype = new JAnnotype();
        annotype.setName("FooBar");

        JVariable element = new JVariable();
        element.setModifiers(new List<>(JModifier.PUBLIC));
        element.setType(new JType("int"));
        element.setName("foo");
        annotype.getElements().addLast(element);

        assertEquals(
            new List<>(
                "@interface FooBar {",
                "    public int foo();",
                "}"
            ),
            writer.write(annotype)
        );
    }

    private void testWriteSingleElementWithDefault() {
        JAnnotype annotype = new JAnnotype();
        annotype.setName("FooBar");

        JVariable element = new JVariable();
        element.setType(new JType("int"));
        element.setName("foo");
        element.setExpression(new List<>(t.number("1")));
        annotype.getElements().addLast(element);

        assertEquals(
            new List<>(
                "@interface FooBar {",
                "    int foo() default 1;",
                "}"
            ),
            writer.write(annotype)
        );
    }

    private void testWriteMultipleElements() {
        JAnnotype annotype = new JAnnotype();
        annotype.setName("Color");

        JVariable red = new JVariable();
        red.setType(new JType("float"));
        red.setName("red");
        annotype.getElements().addLast(red);

        JVariable green = new JVariable();
        green.setType(new JType("float"));
        green.setName("green");
        annotype.getElements().addLast(green);

        JVariable blue = new JVariable();
        blue.setType(new JType("float"));
        blue.setName("blue");
        annotype.getElements().addLast(blue);

        assertEquals(
            new List<>(
                "@interface Color {",
                "    float red();",
                "    float green();",
                "    float blue();",
                "}"
            ),
            writer.write(annotype)
        );
    }

    private void testWriteComplex() {
        JAnnotype annotype = new JAnnotype();
        annotype.setComment("Test documentation comment.");
        annotype.setModifiers(new List<>(JModifier.PUBLIC));
        annotype.setName("FooBar");

        JVariable foo = new JVariable();
        foo.setType(new JType("String"));
        foo.setName("foo");
        annotype.getElements().addLast(foo);

        JVariable bars = new JVariable();
        bars.setModifiers(new List<>(JModifier.PUBLIC));
        bars.setType(new JType("String", null, 1, false));
        bars.setName("bars");
        bars.setExpression(new List<>(b.curlyBrackets()));
        annotype.getElements().addLast(bars);

        assertEquals(
            new List<>(
                "/**",
                " * Test documentation comment.",
                " */",
                "public @interface FooBar {",
                "    String foo();",
                "    public String[] bars() default {};",
                "}"
            ),
            writer.write(annotype)
        );
    }
}
