package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.bounds.JUpperBound;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assert;
import cz.mg.test.Assertions;

public @Test class JUpperBoundWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JUpperBoundWriterTest.class.getSimpleName() + " ... ");

        JUpperBoundWriterTest test = new JUpperBoundWriterTest();
        test.testWriteZero();
        test.testWriteOne();
        test.testWriteTwo();
        test.testWriteThree();
        test.testWriteNamed();

        System.out.println("OK");
    }

    private final @Service JUpperBoundWriter writer = JUpperBoundWriter.getInstance();

    private void testWriteZero() {
        JUpperBound bound = new JUpperBound(null, new List<>());

        Assertions.assertThatCode(() -> writer.write(bound))
            .withMessage("Writer exception should be thrown when upper bounds are missing.")
            .throwsException(WriterException.class);
    }

    private void testWriteOne() {
        JUpperBound bound = new JUpperBound(null, new List<>(new JType("Mouse")));

        String result = writer.write(bound);

        Assert.assertEquals("? extends Mouse", result);
    }

    private void testWriteTwo() {
        JUpperBound bound = new JUpperBound(null, new List<>(
            new JType("Iron"),
            new JType("Mouse")
        ));

        String result = writer.write(bound);

        Assert.assertEquals("? extends Iron & Mouse", result);
    }

    private void testWriteThree() {
        JUpperBound bound = new JUpperBound(null, new List<>(
            new JType("Cute"),
            new JType("Iron"),
            new JType("Mouse")
        ));

        String result = writer.write(bound);

        Assert.assertEquals("? extends Cute & Iron & Mouse", result);
    }

    private void testWriteNamed() {
        JUpperBound bound = new JUpperBound("M", new List<>(new JType("Mouse")));

        String result = writer.write(bound);

        Assert.assertEquals("M extends Mouse", result);
    }
}
