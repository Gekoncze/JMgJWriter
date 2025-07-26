package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.bounds.JLowerBound;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.java.entities.bounds.JUnBound;
import cz.mg.java.entities.bounds.JUpperBound;
import cz.mg.test.Assert;

public @Test class JTypeWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JTypeWriterTest.class.getSimpleName() + " ... ");

        JTypeWriterTest test = new JTypeWriterTest();
        test.testWriteWithNoBounds();
        test.testWriteWithEmptyBounds();
        test.testWriteWithUnBound();
        test.testWriteWithTypeBound();
        test.testWriteWithUpperBound();
        test.testWriteWithLowerBound();
        test.testWriteNested();
        test.testWriteWithMultipleBounds();

        System.out.println("OK");
    }

    private final @Service JTypeWriter writer = JTypeWriter.getInstance();

    private void testWriteWithNoBounds() {
        JType type = new JType("AkiChan", null);

        String result = writer.write(type);

        Assert.assertEquals("AkiChan", result);
    }

    private void testWriteWithEmptyBounds() {
        JType type = new JType("Diamond", new List<>());

        String result = writer.write(type);

        Assert.assertEquals("Diamond<>", result);
    }

    private void testWriteWithUnBound() {
        JType type = new JType("Shelter", new List<>(new JUnBound()));

        String result = writer.write(type);

        Assert.assertEquals("Shelter<?>", result);
    }

    private void testWriteWithTypeBound() {
        JType type = new JType("Shelter", new List<>(new JTypeBound(new JType("Cat"))));

        String result = writer.write(type);

        Assert.assertEquals("Shelter<Cat>", result);
    }

    private void testWriteWithUpperBound() {
        JType type = new JType("Shelter", new List<>(new JUpperBound(null, new List<>(
            new JType("Cat"),
            new JType("Wild")
        ))));

        String result = writer.write(type);

        Assert.assertEquals("Shelter<? extends Cat & Wild>", result);
    }

    private void testWriteWithLowerBound() {
        JType type = new JType("Shelter", new List<>(new JLowerBound(new JType("Cat"))));

        String result = writer.write(type);

        Assert.assertEquals("Shelter<? super Cat>", result);
    }

    private void testWriteNested() {
        JType type = new JType("List", new List<>(
            new JTypeBound(new JType("Array", new List<>(
                new JTypeBound(new JType("Object"))
            )))
        ));

        String result = writer.write(type);

        Assert.assertEquals("List<Array<Object>>", result);
    }

    private void testWriteWithMultipleBounds() {
        JType type = new JType("Map", new List<>(
            new JTypeBound(new JType("Name")),
            new JTypeBound(new JType("Address"))
        ));

        String result = writer.write(type);

        Assert.assertEquals("Map<Name, Address>", result);
    }
}
