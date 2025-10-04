package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.*;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;

import static cz.mg.code.formatter.test.LineAssert.assertEquals;

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
        test.testWritePermits();
        test.testWriteFields();
        test.testWriteInitialiters();
        test.testWriteConstructors();
        test.testWriteMethods();
        test.testWriteStructures();
        test.testWriteComplex();

        System.out.println("OK");
    }

    private final @Service JClassWriter writer = JClassWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteSimple() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        assertEquals(
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

        assertEquals(
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

        assertEquals(
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

        assertEquals(
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

        assertEquals(
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

        assertEquals(
            new List<>(
                "class FooBar implements Foo, Bar {",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWritePermits() {
        JClass jClass = new JClass();
        jClass.setName("Collection");
        jClass.getModifiers().addLast(JModifier.SEALED);

        jClass.getPermits().addLast(new JType("List"));
        jClass.getPermits().addLast(new JType("Array"));
        jClass.getPermits().addLast(new JType("Set"));

        assertEquals(
            new List<>(
                "sealed class Collection permits List, Array, Set {",
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

        assertEquals(
            new List<>(
                "class FooBar {",
                "    Foo foo;",
                "    Bar bar;",
                "}"
            ),
            writer.write(jClass)
        );
    }

    private void testWriteInitialiters() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        jClass.getInitializers().addLast(new JInitializer(new List<>(
            t.word("FOOBAR"),
            t.whitespace(" "),
            t.symbol("="),
            t.whitespace(" "),
            t.number("1"),
            t.symbol(";"),
            t.whitespace("\n"),
            t.word("return"),
            t.symbol(";")
        )));

        jClass.getInitializers().addLast(new JInitializer(new List<>(
            t.word("return"), t.symbol(";")
        )));

        jClass.getInitializers().addLast(new JInitializer(new List<>()));

        assertEquals(
            new List<>(
                "class FooBar {",
                "    static {",
                "        FOOBAR = 1;",
                "        return;",
                "    }",
                "",
                "    static {",
                "        return;",
                "    }",
                "",
                "    static {",
                "    }",
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
        first.setInput(new List<>());
        first.setImplementation(new List<>());

        JVariable parameter = new JVariable();
        parameter.setType(new JType("Object"));
        parameter.setName("o");

        JConstructor second = new JConstructor();
        second.setName("FooBar");
        second.setInput(new List<>(parameter));
        second.setImplementation(new List<>());

        jClass.getConstructors().addLast(first);
        jClass.getConstructors().addLast(second);

        assertEquals(
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

        assertEquals(
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

    private void testWriteStructures() {
        JClass outerClass = new JClass();
        outerClass.setName("OuterClass");

        JClass innerClass = new JClass();
        innerClass.setName("InnerClass");
        outerClass.getStructures().addLast(innerClass);

        JEnum nest = new JEnum();
        nest.setName("Nest");
        innerClass.getStructures().addLast(nest);

        JInterface innerInterface = new JInterface();
        innerInterface.setName("InnerInterface");
        outerClass.getStructures().addLast(innerInterface);

        assertEquals(
            new List<>(
                "class OuterClass {",
                "    class InnerClass {",
                "        enum Nest {",
                "        }",
                "    }",
                "",
                "    interface InnerInterface {",
                "    }",
                "}"
            ),
            writer.write(outerClass)
        );
    }

    private void testWriteComplex() {
        JClass jClass = new JClass();
        jClass.setComment("I slammed my pp in the car door.\nMaow!");
        jClass.setModifiers(new List<>(JModifier.PUBLIC, JModifier.SEALED));
        jClass.setName("Ninu");
        jClass.setBounds(new List<>(new JTypeBound(new JType("V")), new JTypeBound(new JType("T"))));
        jClass.setBase(new JType(
            "VTuber",
            new List<>(new JTypeBound(new JType("V"))),
            0, false
        ));
        jClass.getInterfaces().addLast(new JType("Chaos"));
        jClass.getPermits().addLast(new JType("Violence"));

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

        JInitializer initializer = new JInitializer();
        initializer.setImplementation(new List<>(
            t.word("HEHE"), t.symbol("."), t.word("length"), b.roundBrackets(), t.symbol(";")
        ));
        jClass.getInitializers().addLast(initializer);

        JConstructor constructor = new JConstructor();
        constructor.setModifiers(new List<>(JModifier.PUBLIC));
        constructor.setName("Ninu");
        constructor.setInput(new List<>());
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

        JClass innerThoughts = new JClass();
        innerThoughts.getModifiers().addLast(JModifier.PRIVATE);
        innerThoughts.getModifiers().addLast(JModifier.ABSTRACT);
        innerThoughts.setName("InnerThoughts");
        JMethod meow = new JMethod();
        meow.getModifiers().addLast(JModifier.PRIVATE);
        meow.getModifiers().addLast(JModifier.ABSTRACT);
        meow.setName("meow");
        innerThoughts.getMethods().addLast(meow);
        jClass.getStructures().addLast(innerThoughts);

        assertEquals(
            new List<>(
                "/**",
                " * I slammed my pp in the car door.",
                " * Maow!",
                " */",
                "public sealed class Ninu<V, T> extends VTuber<V> implements Chaos permits Violence {",
                "    public static final String HEHE = \"Hehehehehe!\";",
                "    private int hp = 9;",
                "",
                "    static {",
                "        HEHE.length();",
                "    }",
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
                "",
                "    private abstract class InnerThoughts {",
                "        private abstract void meow();",
                "    }",
                "}"
            ),
            writer.write(jClass)
        );
    }
}
