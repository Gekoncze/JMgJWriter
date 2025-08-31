package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JClass;
import cz.mg.java.entities.JEnum;
import cz.mg.java.entities.JInterface;
import cz.mg.test.Assert;

public @Test class JStructureWritersTest {
    public static void main(String[] args) {
        System.out.print("Running " + JStructureWritersTest.class.getSimpleName() + " ... ");

        JStructureWritersTest test = new JStructureWritersTest();
        test.testWriteClass();
        test.testWriteInterface();
        test.testWriteEnum();

        System.out.println("OK");
    }

    private final @Service JStructureWriters writers = JStructureWriters.getInstance();

    private void testWriteClass() {
        JClass jClass = new JClass();
        jClass.setName("MyClass");

        compare(
            new List<>(
                "class MyClass {",
                "}"
            ),
            writers.write(jClass)
        );
    }

    private void testWriteInterface() {
        JInterface jInterface = new JInterface();
        jInterface.setName("MyInterface");

        compare(
            new List<>(
                "interface MyInterface {",
                "}"
            ),
            writers.write(jInterface)
        );
    }

    private void testWriteEnum() {
        JEnum jEnum = new JEnum();
        jEnum.setName("MyEnum");

        compare(
            new List<>(
                "enum MyEnum {",
                "}"
            ),
            writers.write(jEnum)
        );
    }

    private void compare(@Mandatory List<String> expectations, @Mandatory List<String> reality) {
        Assert.assertThatCollections(expectations, reality)
            .withMessage("Incorrect code generated.")
            .withPrintFunction(s -> '"' + s + '"')
            .areEqual();
    }
}
