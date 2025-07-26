package cz.mg.java.writer.services.type;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.entities.types.JBaseType;
import cz.mg.test.Assert;

public @Test class JBaseTypeWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JBaseTypeWriterTest.class.getSimpleName() + " ... ");

        JBaseTypeWriterTest test = new JBaseTypeWriterTest();
        test.testWrite();

        System.out.println();
    }

    private final @Service JBaseTypeWriter writer = JBaseTypeWriter.getInstance();

    private void testWrite() {
        String result = writer.write(new JBaseType("FooBars"));
        Assert.assertEquals("FooBars", result);
    }
}
