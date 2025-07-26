package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.bounds.*;
import cz.mg.test.Assert;

public @Test class JBoundWritersTest {
    public static void main(String[] args) {
        System.out.print("Running " + JBoundWritersTest.class.getSimpleName() + " ... ");

        JBoundWritersTest test = new JBoundWritersTest();
        test.testWriteUnBound();
        test.testWriteTypeBound();
        test.testWriteUpperBound();
        test.testWriteLowerBound();

        System.out.println("OK");
    }

    private final @Service JBoundWriters writer = JBoundWriters.getInstance();

    private void testWriteUnBound() {
        JBound bound = new JUnBound();

        String result = writer.write(bound);

        Assert.assertEquals("?", result);
    }

    private void testWriteTypeBound() {
        JBound bound = new JTypeBound(new JType("Mouse"));

        String result = writer.write(bound);

        Assert.assertEquals("Mouse", result);
    }

    private void testWriteUpperBound() {
        JBound bound = new JUpperBound(null, new List<>(new JType("Mouse")));

        String result = writer.write(bound);

        Assert.assertEquals("? extends Mouse", result);
    }

    private void testWriteLowerBound() {
        JBound bound = new JLowerBound(new JType("Mouse"));

        String result = writer.write(bound);

        Assert.assertEquals("? super Mouse", result);
    }
}
