package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JImport;
import cz.mg.test.Assert;

public @Test class JImportWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JImportWriterTest.class.getSimpleName() + " ... ");

        JImportWriterTest test = new JImportWriterTest();
        test.testNoPathElements();
        test.testOnePathElement();
        test.testTwoPathElements();
        test.testThreePathElements();

        System.out.println("OK");
    }

    private final @Service JImportWriter writer = JImportWriter.getInstance();

    private void testNoPathElements() {
        String result = writer.write(new JImport());
        Assert.assertEquals("import ;", result);
    }

    private void testOnePathElement() {
        String result = writer.write(new JImport(new List<>("foo")));
        Assert.assertEquals("import foo;", result);
    }

    private void testTwoPathElements() {
        String result = writer.write(new JImport(new List<>("foo", "bar")));
        Assert.assertEquals("import foo.bar;", result);
    }

    private void testThreePathElements() {
        String result = writer.write(new JImport(new List<>("foo", "bar", "foobar")));
        Assert.assertEquals("import foo.bar.foobar;", result);
    }
}
