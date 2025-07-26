package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.bounds.JBound;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.test.Assert;

public @Test class JBoundsWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JBoundsWriterTest.class.getSimpleName() + " ... ");

        JBoundsWriterTest test = new JBoundsWriterTest();
        test.testWriteZeroBounds();
        test.testWriteOneBound();
        test.testWriteTwoBounds();
        test.testWriteThreeBounds();

        System.out.println("OK");
    }

    private final @Service JBoundsWriter writer = JBoundsWriter.getInstance();

    private void testWriteZeroBounds() {
        List<JBound> bounds = new List<>();

        String result = writer.write(bounds);

        Assert.assertEquals("<>", result);
    }

    private void testWriteOneBound() {
        List<JBound> bounds = new List<>(new JTypeBound(new JType("Pangolin")));

        String result = writer.write(bounds);

        Assert.assertEquals("<Pangolin>", result);
    }

    private void testWriteTwoBounds() {
        List<JBound> bounds = new List<>(
            new JTypeBound(new JType("Pangolin")),
            new JTypeBound(new JType("Armadillo"))
        );

        String result = writer.write(bounds);

        Assert.assertEquals("<Pangolin, Armadillo>", result);
    }

    private void testWriteThreeBounds() {
        List<JBound> bounds = new List<>(
            new JTypeBound(new JType("Pangolin")),
            new JTypeBound(new JType("Armadillo")),
            new JTypeBound(new JType("Lemur"))
        );

        String result = writer.write(bounds);

        Assert.assertEquals("<Pangolin, Armadillo, Lemur>", result);
    }
}
