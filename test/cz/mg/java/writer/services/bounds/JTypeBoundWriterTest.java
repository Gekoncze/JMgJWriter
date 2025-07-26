package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.test.Assert;

public @Test class JTypeBoundWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JTypeBoundWriterTest.class.getSimpleName() + " ... ");

        JTypeBoundWriterTest test = new JTypeBoundWriterTest();
        test.testWrite();

        System.out.println("OK");
    }

    private final @Service JTypeBoundWriter writer = JTypeBoundWriter.getInstance();

    private void testWrite() {
        JTypeBound bound = new JTypeBound(new JType("Mouse"));

        String result = writer.write(bound);

        Assert.assertEquals("Mouse", result);
    }
}
