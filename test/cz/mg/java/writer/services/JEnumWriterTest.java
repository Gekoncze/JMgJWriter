package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.*;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

import static cz.mg.java.writer.test.LineAssert.assertEquals;

public @Test class JEnumWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JEnumWriterTest.class.getSimpleName() + " ... ");

        JEnumWriterTest test = new JEnumWriterTest();
        test.testWriteSimple();
        test.testWriteSingleEntry();
        test.testWriteMultipleEntries();
        test.testWriteComplex();

        System.out.println("OK");
    }

    private final @Service JEnumWriter writer = JEnumWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteSimple() {
        JEnum jEnum = new JEnum();
        jEnum.setName("FooBar");

        assertEquals(
            new List<>(
                "enum FooBar {",
                "}"
            ),
            writer.write(jEnum)
        );
    }

    private void testWriteSingleEntry() {
        JEnum jEnum = new JEnum();
        jEnum.setModifiers(new List<>(JModifier.PUBLIC));
        jEnum.setName("Alone");
        jEnum.setEntries(new List<>(
            new JEnumEntry("ALONE", null)
        ));

        assertEquals(
            new List<>(
                "public enum Alone {",
                "    ALONE;",
                "}"
            ),
            writer.write(jEnum)
        );
    }

    private void testWriteMultipleEntries() {
        JEnum jEnum = new JEnum();
        jEnum.setModifiers(new List<>(JModifier.PUBLIC));
        jEnum.setName("DayOfWeek");
        jEnum.setEntries(new List<>(
            new JEnumEntry("MONDAY", null),
            new JEnumEntry("TUESDAY", null),
            new JEnumEntry("WEDNESDAY", null),
            new JEnumEntry("THURSDAY", null),
            new JEnumEntry("FRIDAY", null),
            new JEnumEntry("SATURDAY", null),
            new JEnumEntry("SUNDAY", null)
        ));

        assertEquals(
            new List<>(
                "public enum DayOfWeek {",
                "    MONDAY,",
                "    TUESDAY,",
                "    WEDNESDAY,",
                "    THURSDAY,",
                "    FRIDAY,",
                "    SATURDAY,",
                "    SUNDAY;",
                "}"
            ),
            writer.write(jEnum)
        );
    }

    private void testWriteComplex() {
        JEnum jEnum = new JEnum();
        jEnum.setComment("List of medals.");
        jEnum.setModifiers(new List<>(JModifier.PUBLIC));
        jEnum.setName("Medal");
        jEnum.getInterfaces().addLast(new JType("Rank"));

        jEnum.getEntries().addLast(new JEnumEntry("GOLD", null));
        jEnum.getEntries().addLast(new JEnumEntry("SILVER", new List<>(t.number("2"))));
        jEnum.getEntries().addLast(new JEnumEntry("BRONZE", new List<>(t.number("3"))));

        JVariable minField = new JVariable();
        minField.setModifiers(new List<>(JModifier.PUBLIC, JModifier.STATIC, JModifier.FINAL));
        minField.setType(new JType("int"));
        minField.setName("MIN");
        minField.setExpression(new List<>(t.number("1")));
        jEnum.getFields().addLast(minField);

        JVariable maxField = new JVariable();
        maxField.setModifiers(new List<>(JModifier.PUBLIC, JModifier.STATIC, JModifier.FINAL));
        maxField.setType(new JType("int"));
        maxField.setName("MAX");
        maxField.setExpression(new List<>(t.number("3")));
        jEnum.getFields().addLast(maxField);

        JVariable numberField = new JVariable();
        numberField.setModifiers(new List<>(JModifier.PRIVATE));
        numberField.setType(new JType("int"));
        numberField.setName("rank");
        jEnum.getFields().addLast(numberField);

        JVariable constructorParameter = new JVariable();
        constructorParameter.setType(new JType("int"));
        constructorParameter.setName("rank");

        JConstructor constructor = new JConstructor();
        constructor.setModifiers(new List<>(JModifier.PUBLIC));
        constructor.setName("Medal");
        constructor.setInput(new List<>());
        constructor.setImplementation(new List<>(
            t.word("this"),
            b.roundBrackets(
                t.word("MIN")
            ),
            t.symbol(";")
        ));
        jEnum.getConstructors().addLast(constructor);

        JConstructor parametricConstructor = new JConstructor();
        parametricConstructor.setModifiers(new List<>(JModifier.PUBLIC));
        parametricConstructor.setName("Medal");
        parametricConstructor.setInput(new List<>(constructorParameter));
        parametricConstructor.setImplementation(new List<>(
            t.word("this"),
            t.symbol("."),
            t.word("rank"),
            t.whitespace(" "),
            t.symbol("="),
            t.whitespace(" "),
            t.word("rank"),
            t.symbol(";")
        ));
        jEnum.getConstructors().addLast(parametricConstructor);

        JMethod method = new JMethod();
        method.setComment("Gets rank.");
        method.setModifiers(new List<>(JModifier.PUBLIC));
        method.setOutput(new JType("int"));
        method.setName("getRank");
        method.setImplementation(new List<>(
            t.word("return"),
            t.whitespace(" "),
            t.word("rank"),
            t.symbol(";")
        ));

        jEnum.getMethods().addLast(method);

        assertEquals(
            new List<>(
                "/**",
                " * List of medals.",
                " */",
                "public enum Medal implements Rank {",
                "    GOLD,",
                "    SILVER(2),",
                "    BRONZE(3);",
                "",
                "    public static final int MIN = 1;",
                "    public static final int MAX = 3;",
                "    private int rank;",
                "",
                "    public Medal() {",
                "        this(MIN);",
                "    }",
                "",
                "    public Medal(int rank) {",
                "        this.rank = rank;",
                "    }",
                "",
                "    /**",
                "     * Gets rank.",
                "     */",
                "    public int getRank() {",
                "        return rank;",
                "    }",
                "}"
            ),
            writer.write(jEnum)
        );
    }
}
