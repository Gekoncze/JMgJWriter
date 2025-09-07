package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.entities.JClass;
import cz.mg.java.entities.JEnum;
import cz.mg.java.entities.JInterface;
import cz.mg.java.entities.JRecord;
import cz.mg.test.Assertions;

public @Test class JStructureWritersTest {
    public static void main(String[] args) {
        System.out.print("Running " + JStructureWritersTest.class.getSimpleName() + " ... ");

        JStructureWritersTest test = new JStructureWritersTest();
        test.testWriteClass();
        test.testWriteInterface();
        test.testWriteEnum();
        test.testWriteRecord();

        System.out.println("OK");
    }

    private final @Service JStructureWriters writers = JStructureWriters.getInstance();

    private void testWriteClass() {
        Assertions.assertThat(writers.get(new JClass()))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(JClassWriter.class);
    }

    private void testWriteInterface() {
        Assertions.assertThat(writers.get(new JInterface()))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(JInterfaceWriter.class);
    }

    private void testWriteEnum() {
        Assertions.assertThat(writers.get(new JEnum()))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(JEnumWriter.class);
    }

    private void testWriteRecord() {
        Assertions.assertThat(writers.get(new JRecord()))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(JRecordWriter.class);
    }
}
