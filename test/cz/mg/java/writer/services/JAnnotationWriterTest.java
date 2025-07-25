package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JAnnotation;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assert;
import cz.mg.token.test.TokenFactory;

public @Test class JAnnotationWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JAnnotationWriterTest.class.getSimpleName() + " ... ");

        JAnnotationWriterTest test = new JAnnotationWriterTest();
        test.testHeaderOnly();
        test.testHeaderWithEmptyExpression();
        test.testHeaderWithExpression();
        test.testHeaderWithComment();
        test.testHeaderWithExpressionAndComment();
        test.testMultiLineComment();

        System.out.println("OK");
    }

    private final @Service JAnnotationWriter writer = JAnnotationWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testHeaderOnly() {
        String result = writer.write(new JAnnotation("Field", null, null));
        Assert.assertEquals("@Field", result);
    }

    private void testHeaderWithEmptyExpression() {
        String result = writer.write(new JAnnotation("Field", new List<>(), null));
        Assert.assertEquals("@Field()", result);
    }

    private void testHeaderWithExpression() {
        String result = writer.write(new JAnnotation("Field", new List<>(
            t.word("name"),
            t.whitespace(" "),
            t.symbol("="),
            t.whitespace(" "),
            t.doubleQuote("foo")
        ), null));
        Assert.assertEquals("@Field(name = \"foo\")", result);
    }

    private void testHeaderWithComment() {
        String result = writer.write(new JAnnotation("Field", null, "to be updated"));
        Assert.assertEquals("@Field /* to be updated */", result);
    }

    private void testHeaderWithExpressionAndComment() {
        String result = writer.write(new JAnnotation("Field", new List<>(), "add expression"));
        Assert.assertEquals("@Field() /* add expression */", result);
    }

    private void testMultiLineComment() {
        Assert.assertThatCode(() -> writer.write(new JAnnotation("Field", null, "to be\ncontinued")))
            .withMessage("Multi line comments are not supported.")
            .throwsException(WriterException.class);
    }
}
