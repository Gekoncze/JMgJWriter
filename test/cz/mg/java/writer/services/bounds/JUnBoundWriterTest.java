package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.entities.bounds.JUnBound;
import cz.mg.test.Assert;

public @Test class JUnBoundWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JUnBoundWriterTest.class.getSimpleName() + " ... ");

        JUnBoundWriterTest test = new JUnBoundWriterTest();
        test.testWrite();

        System.out.println("OK");
    }

    private final @Service JUnBoundWriter writer = JUnBoundWriter.getInstance();

    private void testWrite() {
        JUnBound bound = new JUnBound();

        String result = writer.write(bound);

        Assert.assertEquals("?", result);
    }
}
