package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.*;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

import static cz.mg.java.writer.test.QuickAssert.compare;

public @Test class JConstructorWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JConstructorWriterTest.class.getSimpleName() + " ... ");

        JConstructorWriterTest test = new JConstructorWriterTest();
        test.testSimple();
        test.testComplex();

        System.out.println("OK");
    }

    private final @Service JConstructorWriter writer = JConstructorWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testSimple() {
        JConstructor constructor = new JConstructor();
        constructor.setName("FooBar");
        constructor.setImplementation(new List<>());

        compare(
            new List<>(
                "FooBar() {",
                "}"
            ),
            writer.writeLines(constructor)
        );
    }

    private void testComplex() {
        JConstructor constructor = new JConstructor();
        constructor.setComment("My complex test constructor.\nThis test might often fail. :)");
        constructor.getAnnotations().addLast(new JAnnotation("Optional"));
        constructor.getAnnotations().addLast(
            new JAnnotation(
                "Field",
                new List<>(
                    t.word("id"),
                    t.whitespace(" "),
                    t.symbol("="),
                    t.whitespace(" "),
                    t.number("7")
                )
            )
        );
        constructor.getModifiers().addLast(JModifier.PROTECTED);
        constructor.getBounds().addLast(new JTypeBound(new JType("SHOULD_BE_IGNORED")));
        constructor.setOutput(new JType("SHOULD_BE_ALSO_IGNORED"));
        constructor.setName("Complexity");
        constructor.getInput().addLast(new JVariable(
            new List<>(new JAnnotation("Mandatory")),
            new List<>(),
            new JType("Object"),
            "first"
        ));
        constructor.getInput().addLast(new JVariable(
            new List<>(new JAnnotation("Mandatory")),
            new List<>(),
            new JType("Object"),
            "second"
        ));
        constructor.getInput().addLast(new JVariable(
            new List<>(new JAnnotation("Mandatory")),
            new List<>(),
            new JType("Object"),
            "third"
        ));
        constructor.setImplementation(new List<>(
            t.word("super"),
            b.roundBrackets(
                t.word("first"),
                t.symbol(","),
                t.whitespace(" "),
                t.word("second"),
                t.symbol(","),
                t.whitespace(" "),
                t.word("third")
            ),
            t.symbol(";")
        ));

        compare(
            new List<>(
                "/**",
                " * My complex test constructor.",
                " * This test might often fail. :)",
                " */",
                "@Optional",
                "@Field(id = 7)",
                "protected Complexity(@Mandatory Object first, @Mandatory Object second, @Mandatory Object third) {",
                "    super(first, second, third);",
                "}"
            ),
            writer.writeLines(constructor)
        );
    }
}
