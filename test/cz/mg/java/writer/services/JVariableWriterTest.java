package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JAnnotation;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.JVariable;
import cz.mg.test.Assert;
import cz.mg.token.Token;
import cz.mg.token.test.TokenFactory;

public @Test class JVariableWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JVariableWriterTest.class.getSimpleName() + " ... ");

        JVariableWriterTest test = new JVariableWriterTest();
        test.testWriteSimple();
        test.testWriteAnnotations();
        test.testWriteExpression();
        test.testWriteComment();
        test.testWriteComplex();

        System.out.println("OK");
    }


    private final @Service JVariableWriter writer = JVariableWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testWriteSimple() {
        JType type = new JType("int");
        String name = "foo";
        JVariable variable = new JVariable(new List<>(), type, name, null, null);

        String result = writer.write(variable);

        Assert.assertEquals("int foo", result);
    }

    private void testWriteAnnotations() {
        List<JAnnotation> annotations = new List<>(
            new JAnnotation("Required", null, null),
            new JAnnotation("Value", null, null)
        );
        JType type = new JType("int");
        String name = "foo";
        JVariable variable = new JVariable(annotations, type, name, null, null);

        String result = writer.write(variable);

        Assert.assertEquals("@Required @Value int foo", result);
    }


    private void testWriteExpression() {
        JType type = new JType("int");
        String name = "foo";
        List<Token> expression = new List<>(
            t.number("5"),
            t.whitespace(" "),
            t.symbol("+"),
            t.whitespace(" "),
            t.number("1")
        );
        JVariable variable = new JVariable(new List<>(), type, name, expression, null);

        String result = writer.write(variable);

        Assert.assertEquals("int foo = 5 + 1", result);
    }

    private void testWriteComment() {
        JType type = new JType("int");
        String name = "foo";
        String comment = "some comment";
        JVariable variable = new JVariable(new List<>(), type, name, null, comment);

        String result = writer.write(variable);

        // comments should be handled by outer writer to handle delimiters properly
        Assert.assertEquals("int foo", result);
    }

    private void testWriteComplex() {
        List<JAnnotation> annotations = new List<>(
            new JAnnotation("Required", null, null),
            new JAnnotation("Value", null, null)
        );
        JType type = new JType("int");
        String name = "foo";
        List<Token> expression = new List<>(
            t.number("5"),
            t.whitespace(" "),
            t.symbol("+"),
            t.whitespace(" "),
            t.number("1")
        );
        String comment = "some comment";
        JVariable variable = new JVariable(annotations, type, name, expression, comment);

        String result = writer.write(variable);

        Assert.assertEquals("@Required @Value int foo = 5 + 1", result);
    }
}
