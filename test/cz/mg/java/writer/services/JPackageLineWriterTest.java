package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JPackageLine;
import cz.mg.test.Assert;

public @Test class JPackageLineWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JPackageLineWriterTest.class.getSimpleName() + " ... ");

        JPackageLineWriterTest test = new JPackageLineWriterTest();
        test.testNoPathElements();
        test.testOnePathElement();
        test.testTwoPathElements();
        test.testThreePathElements();
        test.testComment();

        System.out.println("OK");
    }

    private final @Service JPackageLineWriter writer = JPackageLineWriter.getInstance();

    private void testNoPathElements() {
        String result = writer.write(new JPackageLine());
        Assert.assertEquals("package ;", result);
    }

    private void testOnePathElement() {
        String result = writer.write(new JPackageLine(new List<>("foo"), null));
        Assert.assertEquals("package foo;", result);
    }

    private void testTwoPathElements() {
        String result = writer.write(new JPackageLine(new List<>("foo", "bar"), null));
        Assert.assertEquals("package foo.bar;", result);
    }

    private void testThreePathElements() {
        String result = writer.write(new JPackageLine(new List<>("foo", "bar", "foobar"), null));
        Assert.assertEquals("package foo.bar.foobar;", result);
    }

    private void testComment() {
        String result = writer.write(new JPackageLine(new List<>("foo", "bar", "foobar"), "temporary"));
        Assert.assertEquals("package foo.bar.foobar; // temporary", result);
    }
}
