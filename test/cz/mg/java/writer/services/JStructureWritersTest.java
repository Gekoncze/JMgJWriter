package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JClass;
import cz.mg.java.entities.JEnum;
import cz.mg.java.entities.JInterface;

import static cz.mg.java.writer.test.LineAssert.assertEquals;

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

        assertEquals(
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

        assertEquals(
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

        assertEquals(
            new List<>(
                "enum MyEnum {",
                "}"
            ),
            writers.write(jEnum)
        );
    }
}
