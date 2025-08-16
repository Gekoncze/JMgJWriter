package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JAnnotation;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.JVariable;
import cz.mg.test.Assert;
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
        JVariable variable = new JVariable(new List<>(), new List<>(), type, name);

        String result = writer.write(variable);

        Assert.assertEquals("int foo", result);
    }

    private void testWriteAnnotations() {
        JVariable variable = new JVariable();
        variable.getAnnotations().addLast(new JAnnotation("Required"));
        variable.getAnnotations().addLast(new JAnnotation("Value"));
        variable.setType(new JType("int"));
        variable.setName("foo");

        String result = writer.write(variable);

        Assert.assertEquals("@Required @Value int foo", result);
    }


    private void testWriteExpression() {
        JVariable variable = new JVariable();
        variable.setType(new JType("int"));
        variable.setName("foo");
        variable.setExpression(new List<>(
            t.number("5"),
            t.whitespace(" "),
            t.symbol("+"),
            t.whitespace(" "),
            t.number("1")
        ));

        String result = writer.write(variable);

        Assert.assertEquals("int foo = 5 + 1", result);
    }

    private void testWriteComment() {
        JVariable variable = new JVariable();
        variable.setType(new JType("int"));
        variable.setName("foo");
        variable.setComment("some comment");

        String result = writer.write(variable);

        // comments should be handled by outer writer to handle delimiters properly
        Assert.assertEquals("int foo", result);
    }

    private void testWriteComplex() {
        JVariable variable = new JVariable();
        variable.getAnnotations().addLast(new JAnnotation("Required"));
        variable.getAnnotations().addLast(new JAnnotation("Value"));
        variable.setType(new JType("int"));
        variable.setName("foo");
        variable.setExpression(new List<>(
            t.number("5"),
            t.whitespace(" "),
            t.symbol("+"),
            t.whitespace(" "),
            t.number("1")
        ));
        variable.setComment("some comment");

        String result = writer.write(variable);

        Assert.assertEquals("@Required @Value int foo = 5 + 1", result);
    }
}
