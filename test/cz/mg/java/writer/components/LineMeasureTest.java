package cz.mg.java.writer.components;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

public @Test class LineMeasureTest {
    public static void main(String[] args) {
        System.out.print("Running " + LineMeasureTest.class.getSimpleName() + " ... ");

        LineMeasureTest test = new LineMeasureTest();
        test.testMeasureNone();
        test.testMeasureIndentation();
        test.testMeasureBrackets();
        test.testMeasurePart();
        test.testMeasureList();
        test.testMeasureAll();

        System.out.println("OK");
    }

    private void testMeasureNone() {
        Assert.assertEquals(0, new LineMeasure().length());
    }

    private void testMeasureIndentation() {
        Assert.assertEquals(4, new LineMeasure().addIndentation(1).length());
        Assert.assertEquals(8, new LineMeasure().addIndentation(2).length());
        Assert.assertEquals(12, new LineMeasure().addIndentation(3).length());
        Assert.assertEquals(
            16,
            new LineMeasure()
                .addIndentation(2)
                .addIndentation(2)
                .length()
        );
    }

    private void testMeasureBrackets() {
        Assert.assertEquals(2, new LineMeasure().addBrackets().length());
        Assert.assertEquals(
            6,
            new LineMeasure()
                .addBrackets()
                .addBrackets()
                .addBrackets()
                .length()
        );
    }

    private void testMeasurePart() {
        Assert.assertEquals(0, new LineMeasure().addPart("").length());
        Assert.assertEquals(7, new LineMeasure().addPart("abcdefg").length());
        Assert.assertEquals(
            10,
            new LineMeasure()
                .addPart("abcdefg")
                .addPart("123")
                .length()
        );
    }

    private void testMeasureList() {
        Assert.assertEquals(0, new LineMeasure().addList(new List<>()).length());
        Assert.assertEquals(3, new LineMeasure().addList(new List<>("abc")).length());
        Assert.assertEquals(8, new LineMeasure().addList(new List<>("abc", "123")).length());
        Assert.assertEquals(17, new LineMeasure().addList(new List<>("abcde", "12345", "...")).length());
    }

    private void testMeasureAll() {
        Assert.assertEquals(
            4 + 2 + 5 + 8,
            new LineMeasure()
                .addIndentation(1)
                .addBrackets()
                .addPart("abcde")
                .addList(new List<>("...", "..."))
                .length()
        );
    }
}
