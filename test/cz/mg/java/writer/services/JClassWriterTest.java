package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.*;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

import static cz.mg.java.writer.test.QuickAssert.compare;

public @Test class JClassWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JClassWriterTest.class.getSimpleName() + " ... ");

        JClassWriterTest test = new JClassWriterTest();
        test.testWriteSimple();
        test.testWriteComment();
        test.testWriteModifiers();
        test.testWriteBounds();
        test.testWriteBase();
        test.testWriteInterfaces();
        test.testWriteFields();
        test.testWriteConstructors();
        test.testWriteMethods();
        test.testWriteComplex();

        System.out.println("OK");
    }

    private final @Service JClassWriter writer = JClassWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteSimple() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        compare(
            new List<>(
                "class FooBar {",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteComment() {
        JClass jClass = new JClass();
        jClass.setComment("Test class to foo and bar.");
        jClass.setName("FooBar");

        compare(
            new List<>(
                "/**",
                " * Test class to foo and bar.",
                " */",
                "class FooBar {",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteModifiers() {
        JClass jClass = new JClass();
        jClass.getModifiers().addLast(JModifier.PUBLIC);
        jClass.setName("FooBar");

        compare(
            new List<>(
                "public class FooBar {",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteBounds() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");
        jClass.getBounds().addLast(new JTypeBound(new JType("T")));

        compare(
            new List<>(
                "class FooBar<T> {",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteBase() {
        JClass jClass = new JClass();
        jClass.setName("Cat");
        jClass.setBase(new JType("Animal"));

        compare(
            new List<>(
                "class Cat extends Animal {",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteInterfaces() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");
        jClass.getInterfaces().addLast(new JType("Foo"));
        jClass.getInterfaces().addLast(new JType("Bar"));

        compare(
            new List<>(
                "class FooBar implements Foo, Bar {",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteFields() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        JVariable fooField = new JVariable();
        fooField.setType(new JType("Foo"));
        fooField.setName("foo");

        JVariable barField = new JVariable();
        barField.setType(new JType("Bar"));
        barField.setName("bar");

        jClass.getFields().addLast(fooField);
        jClass.getFields().addLast(barField);

        compare(
            new List<>(
                "class FooBar {",
                "    Foo foo;",
                "    Bar bar;",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteConstructors() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        JConstructor first = new JConstructor();
        first.setName("FooBar");
        first.setImplementation(new List<>());

        JVariable parameter = new JVariable();
        parameter.setType(new JType("Object"));
        parameter.setName("o");

        JConstructor second = new JConstructor();
        second.setName("FooBar");
        second.setImplementation(new List<>());
        second.getInput().addLast(parameter);

        jClass.getConstructors().addLast(first);
        jClass.getConstructors().addLast(second);

        compare(
            new List<>(
                "class FooBar {",
                "    FooBar() {",
                "    }",
                "",
                "    FooBar(Object o) {",
                "    }",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteMethods() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        JMethod first = new JMethod();
        first.setOutput(null);
        first.setName("firstOne");
        first.setImplementation(new List<>());

        JVariable parameter = new JVariable();
        parameter.setType(new JType("Object"));
        parameter.setName("o");

        JMethod second = new JMethod();
        second.setOutput(new JType("int"));
        second.setName("secondOne");
        second.setImplementation(new List<>(t.word("return"), t.whitespace(" "), t.number("0"), t.symbol(";")));
        second.getInput().addLast(parameter);

        jClass.getMethods().addLast(first);
        jClass.getMethods().addLast(second);

        compare(
            new List<>(
                "class FooBar {",
                "    void firstOne() {",
                "    }",
                "",
                "    int secondOne(Object o) {",
                "        return 0;",
                "    }",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteComplex() {
        JClass jClass = new JClass();
        jClass.setComment("I slammed my pp in the car door.\nMaow!");
        jClass.setModifiers(new List<>(JModifier.PUBLIC, JModifier.FINAL));
        jClass.setName("Ninu");
        jClass.setBounds(new List<>(new JTypeBound(new JType("V")), new JTypeBound(new JType("T"))));
        jClass.setBase(new JType(
            "VTuber",
            new List<>(new JTypeBound(new JType("V"))),
            0, false
        ));
        jClass.getInterfaces().addLast(new JType("Chaos"));

        JVariable staticField = new JVariable();
        staticField.setModifiers(new List<>(JModifier.PUBLIC, JModifier.STATIC, JModifier.FINAL));
        staticField.setType(new JType("String"));
        staticField.setName("HEHE");
        staticField.setExpression(new List<>(t.doubleQuote("Hehehehehe!")));
        jClass.getFields().addLast(staticField);

        JVariable regularField = new JVariable();
        regularField.setModifiers(new List<>(JModifier.PRIVATE));
        regularField.setType(new JType("int"));
        regularField.setName("hp");
        regularField.setExpression(new List<>(t.number("9")));
        jClass.getFields().addLast(regularField);

        JConstructor constructor = new JConstructor();
        constructor.setModifiers(new List<>(JModifier.PUBLIC));
        constructor.setName("Ninu");
        constructor.setImplementation(new List<>());
        jClass.getConstructors().addLast(constructor);

        JMethod method = new JMethod();
        method.setComment("I'm in danger!");
        method.setModifiers(new List<>(JModifier.PUBLIC));
        method.setName("moida");

        JVariable firstParameter = new JVariable();
        firstParameter.setType(new JType("Weapon"));
        firstParameter.setName("weapon");

        JVariable secondParameter = new JVariable();
        secondParameter.setType(new JType("Person"));
        secondParameter.setName("target");

        method.getInput().addLast(firstParameter);
        method.getInput().addLast(secondParameter);
        method.setImplementation(new List<>(
            t.word("weapon"),
            t.symbol("."),
            t.word("hit"),
            b.roundBrackets(
                t.word("target")
            ),
            t.symbol(";")
        ));

        jClass.getMethods().addLast(method);

        compare(
            new List<>(
                "/**",
                " * I slammed my pp in the car door.",
                " * Maow!",
                " */",
                "public final class Ninu<V, T> extends VTuber<V> implements Chaos {",
                "    public static final String HEHE = \"Hehehehehe!\";",
                "    private int hp = 9;",
                "",
                "    public Ninu() {",
                "    }",
                "",
                "    /**",
                "     * I'm in danger!",
                "     */",
                "    public void moida(Weapon weapon, Person target) {",
                "        weapon.hit(target);",
                "    }",
                "}"
            ),
            writer.write(jClass)
        );
    }
}
