package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.bounds.JLowerBound;
import cz.mg.test.Assert;

public @Test class JLowerBoundWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JLowerBoundWriterTest.class.getSimpleName() + " ... ");

        JLowerBoundWriterTest test = new JLowerBoundWriterTest();
        test.testWrite();

        System.out.println("OK");
    }

    private final @Service JLowerBoundWriter writer = JLowerBoundWriter.getInstance();

    private void testWrite() {
        JLowerBound bound = new JLowerBound(new JType("Mouse"));

        String result = writer.write(bound);

        Assert.assertEquals("? super Mouse", result);
    }
}
