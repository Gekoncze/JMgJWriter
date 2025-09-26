package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.entities.*;
import cz.mg.test.Assertions;

public @Test class JStructureWritersTest {
    public static void main(String[] args) {
        System.out.print("Running " + JStructureWritersTest.class.getSimpleName() + " ... ");

        JStructureWritersTest test = new JStructureWritersTest();
        test.testWriteClass();
        test.testWriteInterface();
        test.testWriteEnum();
        test.testWriteRecord();
        test.testWriteAnnotype();

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

    private void testWriteAnnotype() {
        Assertions.assertThat(writers.get(new JAnnotype()))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(JAnnotypeWriter.class);
    }
}
