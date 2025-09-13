package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.java.entities.*;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.java.entities.bounds.JUpperBound;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

import static cz.mg.java.writer.test.LineAssert.assertEquals;

public @Test class JMethodWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JMethodWriterTest.class.getSimpleName() + " ... ");

        JMethodWriterTest test = new JMethodWriterTest();
        test.testSimple();
        test.testComment();
        test.testSingleAnnotation();
        test.testMultipleAnnotation();
        test.testSingleModifier();
        test.testMultipleModifiers();
        test.testSingleBound();
        test.testMultipleBounds();
        test.testOutput();
        test.testSingleInput();
        test.testMultipleInput();
        test.testParametersOnSingleLine();
        test.testParametersOnSeparateLines();
        test.testEmptyImplementation();
        test.testSingleLineImplementation();
        test.testMultiLineImplementation();
        test.testComplex();

        System.out.println("OK");
    }

    private final @Service JMethodWriter writer = JMethodWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testSimple() {
        JMethod method = new JMethod();
        method.setName("fooBar");

        assertEquals(
            new List<>(
                "void fooBar();"
            ),
            writer.writeLines(method)
        );
    }

    private void testComment() {
        JMethod method = new JMethod();
        method.setComment("My fancy test method.");
        method.setName("fooBar");

        assertEquals(
            new List<>(
                "/**",
                " * My fancy test method.",
                " */",
                "void fooBar();"
            ),
            writer.writeLines(method)
        );
    }

    private void testSingleAnnotation() {
        JMethod method = new JMethod();
        method.getAnnotations().addLast(new JAnnotation("Optional"));
        method.setName("fooBar");

        assertEquals(
            new List<>(
                "@Optional",
                "void fooBar();"
            ),
            writer.writeLines(method)
        );
    }

    private void testMultipleAnnotation() {
        JMethod method = new JMethod();
        method.getAnnotations().addLast(new JAnnotation("Optional"));
        method.getAnnotations().addLast(new JAnnotation("Part"));
        method.setName("fooBar");

        assertEquals(
            new List<>(
                "@Optional",
                "@Part",
                "void fooBar();"
            ),
            writer.writeLines(method)
        );
    }

    private void testSingleModifier() {
        JMethod method = new JMethod();
        method.getModifiers().addLast(JModifier.PUBLIC);
        method.setName("fooBar");

        assertEquals(
            new List<>(
                "public void fooBar();"
            ),
            writer.writeLines(method)
        );
    }

    private void testMultipleModifiers() {
        JMethod method = new JMethod();
        method.getModifiers().addLast(JModifier.PUBLIC);
        method.getModifiers().addLast(JModifier.ABSTRACT);
        method.setName("fooBar");

        assertEquals(
            new List<>(
                "public abstract void fooBar();"
            ),
            writer.writeLines(method)
        );
    }

    private void testSingleBound() {
        JMethod method = new JMethod();
        method.getBounds().addLast(new JTypeBound(new JType("T")));
        method.setName("fooBar");

        assertEquals(
            new List<>(
                "<T> void fooBar();"
            ),
            writer.writeLines(method)
        );
    }

    private void testMultipleBounds() {
        JMethod method = new JMethod();
        method.getBounds().addLast(new JTypeBound(new JType("U")));
        method.getBounds().addLast(new JTypeBound(new JType("V")));
        method.setName("fooBar");

        assertEquals(
            new List<>(
                "<U, V> void fooBar();"
            ),
            writer.writeLines(method)
        );
    }

    private void testOutput() {
        JMethod method = new JMethod();
        method.setOutput(new JType("int"));
        method.setName("fooBar");

        assertEquals(
            new List<>(
                "int fooBar();"
            ),
            writer.writeLines(method)
        );
    }

    private void testSingleInput() {
        JMethod method = new JMethod();
        method.setName("fooBar");
        method.getInput().addLast(new JVariable(new List<>(), new List<>(), new JType("int"), "a"));

        assertEquals(
            new List<>(
                "void fooBar(int a);"
            ),
            writer.writeLines(method)
        );
    }

    private void testMultipleInput() {
        JMethod method = new JMethod();
        method.setName("fooBar");
        method.getInput().addLast(new JVariable(new List<>(), new List<>(), new JType("int"), "a"));
        method.getInput().addLast(new JVariable(new List<>(), new List<>(), new JType("int"), "b"));

        assertEquals(
            new List<>(
                "void fooBar(int a, int b);"
            ),
            writer.writeLines(method)
        );
    }

    private void testParametersOnSingleLine() {
        JMethod method = new JMethod();
        method.setName("fooBar");
        method.getInput().addCollectionLast(new Array<>(
            new JVariable(new List<>(new JAnnotation("Mandatory")), new List<>(), new JType("Integer"), "a"),
            new JVariable(new List<>(new JAnnotation("Mandatory")), new List<>(), new JType("Integer"), "b"),
            new JVariable(new List<>(new JAnnotation("Mandatory")), new List<>(), new JType("Integer"), "c"),
            new JVariable(new List<>(new JAnnotation("Mandatory")), new List<>(), new JType("Integer"), "d")
        ));

        assertEquals(
            new List<>(
                "void fooBar(@Mandatory Integer a, @Mandatory Integer b, @Mandatory Integer c, @Mandatory Integer d);"
            ),
            writer.writeLines(method)
        );
    }

    private void testParametersOnSeparateLines() {
        JMethod method = new JMethod();
        method.setName("fooBar");
        method.getInput().addCollectionLast(new Array<>(
            new JVariable(new List<>(new JAnnotation("Mandatory")), new List<>(), new JType("VeryLongClassName"), "a"),
            new JVariable(new List<>(new JAnnotation("Mandatory")), new List<>(), new JType("VeryLongClassName"), "b"),
            new JVariable(new List<>(new JAnnotation("Mandatory")), new List<>(), new JType("VeryLongClassName"), "c"),
            new JVariable(new List<>(new JAnnotation("Mandatory")), new List<>(), new JType("VeryLongClassName"), "d")
        ));

        assertEquals(
            new List<>(
                "void fooBar(",
                "    @Mandatory VeryLongClassName a,",
                "    @Mandatory VeryLongClassName b,",
                "    @Mandatory VeryLongClassName c,",
                "    @Mandatory VeryLongClassName d",
                ");"
            ),
            writer.writeLines(method)
        );
    }

    private void testEmptyImplementation() {
        JMethod method = new JMethod();
        method.setName("fooBar");
        method.setImplementation(new List<>());

        assertEquals(
            new List<>(
                "void fooBar() {",
                "}"
            ),
            writer.writeLines(method)
        );
    }

    private void testSingleLineImplementation() {
        JMethod method = new JMethod();
        method.setName("singleLine");
        method.setImplementation(new List<>(t.word("return"), t.symbol(";")));

        assertEquals(
            new List<>(
                "void singleLine() {",
                "    return;",
                "}"
            ),
            writer.writeLines(method)
        );
    }

    private void testMultiLineImplementation() {
        JMethod method = new JMethod();
        method.setOutput(new JType("int"));
        method.setName("multiLine");
        method.setImplementation(new List<>(
            t.word("int"), t.whitespace(" "), t.word("a"), t.symbol(";"), t.whitespace("\n"),
            t.word("return"), t.whitespace(" "), t.word("a"), t.symbol(";")
        ));

        assertEquals(
            new List<>(
                "int multiLine() {",
                "    int a;",
                "    return a;",
                "}"
            ),
            writer.writeLines(method)
        );
    }

    private void testComplex() {
        JMethod method = new JMethod();
        method.setComment("My complex test method.\nThis test might often fail. :)");
        method.getAnnotations().addLast(new JAnnotation("Optional"));
        method.getAnnotations().addLast(
            new JAnnotation(
                "Field",
                new List<>(
                    t.word("id"),
                    t.whitespace(" "),
                    t.symbol("="),
                    t.whitespace(" "),
                    t.number("5")
                )
            )
        );
        method.getModifiers().addLast(JModifier.PROTECTED);
        method.getModifiers().addLast(JModifier.VOLATILE);
        method.getBounds().addLast(new JTypeBound(new JType("T")));
        method.getBounds().addLast(new JUpperBound(null, new List<>(new JType("Number"))));
        method.setOutput(new JType(
            "List",
            new List<>(new JTypeBound(new JType("T"))),
            0, false
        ));
        method.setName("complexity");
        method.getInput().addLast(new JVariable(
            new List<>(new JAnnotation("Mandatory")),
            new List<>(),
            new JType("Object"),
            "first"
        ));
        method.getInput().addLast(new JVariable(
            new List<>(new JAnnotation("Mandatory")),
            new List<>(),
            new JType("Object"),
            "second"
        ));
        method.getInput().addLast(new JVariable(
            new List<>(new JAnnotation("Mandatory")),
            new List<>(),
            new JType("Object"),
            "third"
        ));
        method.setImplementation(new List<>(
            t.word("return"),
            t.whitespace(" "),
            t.word("new"),
            t.whitespace(" "),
            t.word("List"),
            t.symbol("<"),
            t.symbol(">"),
            b.roundBrackets(),
            t.symbol(";")
        ));

        assertEquals(
            new List<>(
                "/**",
                " * My complex test method.",
                " * This test might often fail. :)",
                " */",
                "@Optional",
                "@Field(id = 5)",
                "protected volatile <T, ? extends Number> List<T> complexity(",
                "    @Mandatory Object first,",
                "    @Mandatory Object second,",
                "    @Mandatory Object third",
                ") {",
                "    return new List<>();",
                "}"
            ),
            writer.writeLines(method)
        );
    }
}
