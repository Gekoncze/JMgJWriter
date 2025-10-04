package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.code.formatter.test.LineAssert;
import cz.mg.collections.list.List;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assert;
import cz.mg.test.Assertions;

public @Test class JCommentWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + JCommentWriterTest.class.getSimpleName() + " ... ");

        JCommentWriterTest test = new JCommentWriterTest();
        test.testWriteSingleLineComment();
        test.testWriteSingleLineStarComment();
        test.testWriteMultiLineComment();
        test.testWriteDocumentationComment();

        System.out.println("OK");
    }

    private final @Service JCommentWriter writer = JCommentWriter.getInstance();

    private void testWriteSingleLineComment() {
        Assert.assertEquals("//", writer.writeSingleLineComment(""));
        Assert.assertEquals("// ", writer.writeSingleLineComment(" "));
        Assert.assertEquals("// foo bar", writer.writeSingleLineComment("foo bar"));

        Assertions.assertThatCode(() -> writer.writeSingleLineComment("\n"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }

    private void testWriteSingleLineStarComment() {
        Assert.assertEquals("/**/", writer.writeSingleLineStarComment(""));
        Assert.assertEquals("/* */", writer.writeSingleLineStarComment(" "));
        Assert.assertEquals("/* foo bar */", writer.writeSingleLineStarComment("foo bar"));

        Assertions.assertThatCode(() -> writer.writeSingleLineStarComment("\n"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> writer.writeSingleLineStarComment("*/"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }

    private void testWriteMultiLineComment() {
        LineAssert.assertEquals(new List<>("/**/"), writer.writeMultiLineComment(""));
        LineAssert.assertEquals(new List<>("/* */"), writer.writeMultiLineComment(" "));
        LineAssert.assertEquals(new List<>("/*", "*/"), writer.writeMultiLineComment("\n"));
        LineAssert.assertEquals(new List<>("/* foo bar */"), writer.writeMultiLineComment("foo bar"));
        LineAssert.assertEquals(new List<>("/* foo", "bar */"), writer.writeMultiLineComment("foo\nbar"));
        LineAssert.assertEquals(new List<>("/*", "foo", "bar", "*/"), writer.writeMultiLineComment("\nfoo\nbar\n"));

        Assertions.assertThatCode(() -> writer.writeMultiLineComment("*/"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }

    private void testWriteDocumentationComment() {
        LineAssert.assertEquals(
            new List<>(
                "/**",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("")
        );

        LineAssert.assertEquals(
            new List<>(
                "/**",
                " *",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("\n")
        );

        LineAssert.assertEquals(
            new List<>(
                "/**",
                " *",
                " *",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("\n\n")
        );

        LineAssert.assertEquals(
            new List<>(
                "/**",
                " *  ",
                " */"
            ),
            writer.writeDocumentationComment(" ")
        );

        LineAssert.assertEquals(
            new List<>(
                "/**",
                " * \t",
                " */"
            ),
            writer.writeDocumentationComment("\t")
        );

        LineAssert.assertEquals(
            new List<>(
                "/**",
                " * foo bar",
                " */"
            ),
            writer.writeDocumentationComment("foo bar")
        );

        LineAssert.assertEquals(
            new List<>(
                "/**",
                " *",
                " * foo bar",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("\nfoo bar\n")
        );

        LineAssert.assertEquals(
            new List<>(
                "/**",
                " * foo",
                " * bar",
                " */"
            ),
            writer.writeDocumentationComment("foo\nbar")
        );

        LineAssert.assertEquals(
            new List<>(
                "/**",
                " *",
                " * foo",
                " * bar",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("\nfoo\nbar\n")
        );

        Assertions.assertThatCode(() -> writer.writeDocumentationComment("*/"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }
}
