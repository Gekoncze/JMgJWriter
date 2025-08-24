package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.entities.*;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.test.Assert;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

public @Test class JInterfaceWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JInterfaceWriterTest.class.getSimpleName() + " ... ");

        JInterfaceWriterTest test = new JInterfaceWriterTest();
        test.testWriteSimple();
        test.testWriteComplex();

        System.out.println("OK");
    }

    private final @Service JInterfaceWriter writer = JInterfaceWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteSimple() {
        JInterface jInterface = new JInterface();
        jInterface.setName("FooBar");

        compare(
            new List<>(
                "interface FooBar {",
                "}"
            ),
            writer.write(jInterface)
        );
    }

    private void testWriteComplex() {
        JInterface jInterface = new JInterface();
        jInterface.setComment("Test documentation comment.\nMore documentation...");
        jInterface.setModifiers(new List<>(JModifier.PUBLIC));
        jInterface.setName("FooBar");
        jInterface.setBounds(new List<>(new JTypeBound(new JType("F")), new JTypeBound(new JType("B"))));
        jInterface.getInterfaces().addLast(new JType("Few"));
        jInterface.getInterfaces().addLast(new JType("Bars"));

        JVariable staticField = new JVariable();
        staticField.setModifiers(new List<>(JModifier.PUBLIC, JModifier.STATIC, JModifier.FINAL));
        staticField.setType(new JType("String", null, 1, false));
        staticField.setName("BARS");
        staticField.setExpression(new List<>(t.word("null")));
        jInterface.getFields().addLast(staticField);

        JMethod method = new JMethod();
        method.setComment("Drops given bars.");
        method.setModifiers(new List<>(JModifier.PUBLIC));
        method.setName("dropBars");

        JVariable parameter = new JVariable();
        parameter.setType(new JType("Bar", null, 0, true));
        parameter.setName("bars");
        method.getInput().addLast(parameter);

        jInterface.getMethods().addLast(method);

        compare(
            new List<>(
                "/**",
                " * Test documentation comment.",
                " * More documentation...",
                " */",
                "public interface FooBar<F, B> extends Few, Bars {",
                "    public static final String[] BARS = null;",
                "",
                "    /**",
                "     * Drops given bars.",
                "     */",
                "    public void dropBars(Bar... bars);",
                "}"
            ),
            writer.write(jInterface)
        );
    }

    private void compare(@Mandatory List<String> expectations, @Mandatory List<String> reality) {
        Assert.assertThatCollections(expectations, reality)
            .withMessage("Incorrect interface code generated.")
            .withPrintFunction(s -> '"' + s + '"')
            .areEqual();
    }
}
