package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JModifier;
import cz.mg.test.Assert;

public @Test class JModifierWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JModifierWriterTest.class.getSimpleName() + " ... ");

        JModifierWriterTest test = new JModifierWriterTest();
        test.testWriteModifiers();
        test.testWriteEmpty();
        test.testWriteSingle();
        test.testWriteMultiple();

        System.out.println("OK");
    }

    private final @Service JModifierWriter writer = JModifierWriter.getInstance();

    private void testWriteModifiers() {
        Assert.assertEquals("abstract", writer.write(JModifier.ABSTRACT));
        Assert.assertEquals("private", writer.write(JModifier.PRIVATE));
        Assert.assertEquals("protected", writer.write(JModifier.PROTECTED));
        Assert.assertEquals("public", writer.write(JModifier.PUBLIC));
        Assert.assertEquals("final", writer.write(JModifier.FINAL));
        Assert.assertEquals("static", writer.write(JModifier.STATIC));
        Assert.assertEquals("volatile", writer.write(JModifier.VOLATILE));
    }

    private void testWriteEmpty() {
        Assert.assertEquals("", writer.write(new List<>()));
    }

    private void testWriteSingle() {
        Assert.assertEquals("final", writer.write(new List<>(JModifier.FINAL)));
    }

    private void testWriteMultiple() {
        Assert.assertEquals(
            "public static final",
            writer.write(new List<>(JModifier.PUBLIC, JModifier.STATIC, JModifier.FINAL))
        );
    }
}
