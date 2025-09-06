package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.bounds.JLowerBound;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.java.entities.bounds.JUnBound;
import cz.mg.java.entities.bounds.JUpperBound;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assert;
import cz.mg.test.Assertions;

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
        test.testWriteArrays();
        test.testWriteVarargs();
        test.testWriteComplex();

        System.out.println("OK");
    }

    private final @Service JTypeWriter writer = JTypeWriter.getInstance();

    private void testWriteWithNoBounds() {
        JType type = new JType();
        type.setName("AkiChan");

        String result = writer.write(type);

        Assert.assertEquals("AkiChan", result);
    }

    private void testWriteWithEmptyBounds() {
        JType type = new JType();
        type.setName("Diamond");
        type.setBounds(new List<>());

        String result = writer.write(type);

        Assert.assertEquals("Diamond<>", result);
    }

    private void testWriteWithUnBound() {
        JType type = new JType();
        type.setName("Shelter");
        type.setBounds(new List<>(new JUnBound()));

        String result = writer.write(type);

        Assert.assertEquals("Shelter<?>", result);
    }

    private void testWriteWithTypeBound() {
        JType type = new JType();
        type.setName("Shelter");
        type.setBounds(new List<>(new JTypeBound(new JType("Cat"))));

        String result = writer.write(type);

        Assert.assertEquals("Shelter<Cat>", result);
    }

    private void testWriteWithUpperBound() {
        JType type = new JType();
        type.setName("Shelter");
        type.setBounds(new List<>(new JUpperBound(null, new List<>(
            new JType("Cat"),
            new JType("Wild")
        ))));

        String result = writer.write(type);

        Assert.assertEquals("Shelter<? extends Cat & Wild>", result);
    }

    private void testWriteWithLowerBound() {
        JType type = new JType();
        type.setName("Shelter");
        type.setBounds(new List<>(new JLowerBound(new JType("Cat"))));

        String result = writer.write(type);

        Assert.assertEquals("Shelter<? super Cat>", result);
    }

    private void testWriteNested() {
        JType type = new JType();
        type.setName("List");
        type.setBounds(new List<>(
            new JTypeBound(new JType("Array", new List<>(
                new JTypeBound(new JType("Object"))
            ), 0, false))
        ));

        String result = writer.write(type);

        Assert.assertEquals("List<Array<Object>>", result);
    }

    private void testWriteWithMultipleBounds() {
        JType type = new JType();
        type.setName("Map");
        type.setBounds(new List<>(
            new JTypeBound(new JType("Name")),
            new JTypeBound(new JType("Address"))
        ));

        String result = writer.write(type);

        Assert.assertEquals("Map<Name, Address>", result);
    }

    private void testWriteArrays() {
        JType d0 = new JType();
        d0.setName("Object");
        d0.setDimensions(0);

        JType d1 = new JType();
        d1.setName("Object");
        d1.setDimensions(1);

        JType d2 = new JType();
        d2.setName("Object");
        d2.setDimensions(2);

        JType d3 = new JType();
        d3.setName("Object");
        d3.setDimensions(3);

        JType illegal = new JType();
        illegal.setName("Object");
        illegal.setDimensions(-1);

        String result0 = writer.write(d0);
        String result1 = writer.write(d1);
        String result2 = writer.write(d2);
        String result3 = writer.write(d3);

        Assert.assertEquals("Object", result0);
        Assert.assertEquals("Object[]", result1);
        Assert.assertEquals("Object[][]", result2);
        Assert.assertEquals("Object[][][]", result3);

        Assertions.assertThatCode(() -> writer.write(illegal))
            .withMessage("Writer exception should be thrown for illegal array dimension.")
            .throwsException(WriterException.class);
    }

    private void testWriteVarargs() {
        JType type = new JType();
        type.setName("Object");
        type.setVarargs(true);

        String result = writer.write(type);

        Assert.assertEquals("Object...", result);
    }

    private void testWriteComplex() {
        JType type = new JType();
        type.setName("World");
        type.setBounds(new List<>(new JUpperBound("A", new List<>(
            new JType("Tee"),
            new JType("Hee")
        ))));
        type.setDimensions(2);
        type.setVarargs(true);

        String result = writer.write(type);

        Assert.assertEquals("World<A extends Tee & Hee>[][]...", result);
    }
}
