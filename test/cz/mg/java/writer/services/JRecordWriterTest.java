package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.*;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

import static cz.mg.java.writer.test.LineAssert.assertEquals;

public @Test class JRecordWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JRecordWriterTest.class.getSimpleName() + " ... ");

        JRecordWriterTest test = new JRecordWriterTest();
        test.testWriteSimple();
        test.testWritePropertiesSingleLine();
        test.testWritePropertiesMultiLine();
        test.testWriteComplex();

        System.out.println("OK");
    }

    private final @Service JRecordWriter writer = JRecordWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteSimple() {
        JRecord jRecord = new JRecord();
        jRecord.setName("FooBar");

        assertEquals(
            new List<>(
                "record FooBar() {",
                "}"
            ),
            writer.write(jRecord)
        );
    }

    private void testWritePropertiesSingleLine() {
        JRecord jRecord = new JRecord();
        jRecord.setName("SuperSpecialKindOfColor");

        JVariable property1 = new JVariable();
        property1.setType(new JType("int"));
        property1.setName("red");
        jRecord.getProperties().addLast(property1);

        JVariable property2 = new JVariable();
        property2.setType(new JType("int"));
        property2.setName("green");
        jRecord.getProperties().addLast(property2);

        JVariable property3 = new JVariable();
        property3.setType(new JType("int"));
        property3.setName("blue");
        jRecord.getProperties().addLast(property3);

        JVariable property4 = new JVariable();
        property4.setType(new JType("int"));
        property4.setName("alpha");
        jRecord.getProperties().addLast(property4);

        assertEquals(
            new List<>(
                "record SuperSpecialKindOfColor(int red, int green, int blue, int alpha) {",
                "}"
            ),
            writer.write(jRecord)
        );
    }

    private void testWritePropertiesMultiLine() {
        JRecord jRecord = new JRecord();
        jRecord.setName("SuperSpecialKindOfColor");

        JVariable property1 = new JVariable();
        property1.getAnnotations().addLast(new JAnnotation("Mandatory"));
        property1.setType(new JType("Integer"));
        property1.setName("red");
        jRecord.getProperties().addLast(property1);

        JVariable property2 = new JVariable();
        property2.getAnnotations().addLast(new JAnnotation("Mandatory"));
        property2.setType(new JType("Integer"));
        property2.setName("green");
        jRecord.getProperties().addLast(property2);

        JVariable property3 = new JVariable();
        property3.getAnnotations().addLast(new JAnnotation("Mandatory"));
        property3.setType(new JType("Integer"));
        property3.setName("blue");
        jRecord.getProperties().addLast(property3);

        JVariable property4 = new JVariable();
        property4.getAnnotations().addLast(new JAnnotation("Mandatory"));
        property4.setType(new JType("Integer"));
        property4.setName("alpha");
        jRecord.getProperties().addLast(property4);

        assertEquals(
            new List<>(
                "record SuperSpecialKindOfColor(",
                "    @Mandatory Integer red,",
                "    @Mandatory Integer green,",
                "    @Mandatory Integer blue,",
                "    @Mandatory Integer alpha",
                ") {",
                "}"
            ),
            writer.write(jRecord)
        );
    }

    private void testWriteComplex() {
        JRecord jRecord = new JRecord();
        jRecord.setComment("You get a prize!\n... if you win.");
        jRecord.setModifiers(new List<>(JModifier.PUBLIC));
        jRecord.setName("WorldRecord");
        jRecord.setBounds(new List<>(new JTypeBound(new JType("R"))));
        jRecord.getInterfaces().addLast(new JType("Rank"));

        JVariable firstProperty = new JVariable();
        firstProperty.getAnnotations().addLast(new JAnnotation("Mandatory"));
        firstProperty.setType(new JType("String"));
        firstProperty.setName("discipline");
        jRecord.getProperties().addLast(firstProperty);

        JVariable secondProperty = new JVariable();
        secondProperty.getAnnotations().addLast(new JAnnotation("Optional"));
        secondProperty.setType(new JType("Date"));
        secondProperty.setName("date");
        jRecord.getProperties().addLast(secondProperty);

        JVariable staticField = new JVariable();
        staticField.setModifiers(new List<>(JModifier.PRIVATE, JModifier.STATIC, JModifier.FINAL));
        staticField.setType(new JType("String", null, 0, false));
        staticField.setName("CAKE");
        staticField.setExpression(new List<>(t.word("null")));
        jRecord.getFields().addLast(staticField);

        JInitializer initializer = new JInitializer();
        initializer.setImplementation(new List<>(
            t.word("eat"),
            b.roundBrackets(t.word("CAKE")),
            t.symbol(";")
        ));
        jRecord.getInitializers().addLast(initializer);

        JConstructor constructor = new JConstructor();
        constructor.setModifiers(new List<>(JModifier.PUBLIC));
        constructor.setName("WorldRecord");
        constructor.setImplementation(new List<>(
            t.word("if"),
            t.whitespace(" "),
            b.roundBrackets(
                t.word("discipline"),
                t.symbol("."),
                t.word("isBlank"),
                b.roundBrackets()
            ),
            t.whitespace(" "),
            t.word("throw"),
            t.whitespace(" "),
            t.word("new"),
            t.whitespace(" "),
            t.word("IllegalArgumentException"),
            b.roundBrackets(),
            t.symbol(";")
        ));
        jRecord.getConstructors().addLast(constructor);

        JMethod method = new JMethod();
        method.getAnnotations().addLast(new JAnnotation("Override"));
        method.setComment("Gets rank.");
        method.setModifiers(new List<>(JModifier.PUBLIC));
        method.setOutput(new JType("int"));
        method.setName("getRank");
        method.setImplementation(new List<>());

        jRecord.getMethods().addLast(method);

        JRecord person = new JRecord();
        person.getModifiers().addLast(JModifier.PUBLIC);
        person.setName("Person");
        jRecord.getStructures().addLast(person);

        assertEquals(
            new List<>(
                "/**",
                " * You get a prize!",
                " * ... if you win.",
                " */",
                "public record WorldRecord<R>(@Mandatory String discipline, @Optional Date date) implements Rank {",
                "    private static final String CAKE = null;",
                "",
                "    static {",
                "        eat(CAKE);",
                "    }",
                "",
                "    public WorldRecord {",
                "        if (discipline.isBlank()) throw new IllegalArgumentException();",
                "    }",
                "",
                "    /**",
                "     * Gets rank.",
                "     */",
                "    @Override",
                "    public int getRank() {",
                "    }",
                "",
                "    public record Person() {",
                "    }",
                "}"
            ),
            writer.write(jRecord)
        );
    }
}
