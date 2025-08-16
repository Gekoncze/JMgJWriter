package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assert;

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

        Assert.assertThatCode(() -> writer.writeSingleLineComment("\n"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }

    private void testWriteSingleLineStarComment() {
        Assert.assertEquals("/**/", writer.writeSingleLineStarComment(""));
        Assert.assertEquals("/* */", writer.writeSingleLineStarComment(" "));
        Assert.assertEquals("/* foo bar */", writer.writeSingleLineStarComment("foo bar"));

        Assert.assertThatCode(() -> writer.writeSingleLineStarComment("\n"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> writer.writeSingleLineStarComment("*/"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }

    private void testWriteMultiLineComment() {
        Assert.assertThatCollections(new List<>("/**/"), writer.writeMultiLineComment("")).areEqual();
        Assert.assertThatCollections(new List<>("/* */"), writer.writeMultiLineComment(" ")).areEqual();
        Assert.assertThatCollections(new List<>("/*", "*/"), writer.writeMultiLineComment("\n")).areEqual();
        Assert.assertThatCollections(new List<>("/* foo bar */"), writer.writeMultiLineComment("foo bar")).areEqual();
        Assert.assertThatCollections(new List<>("/* foo", "bar */"), writer.writeMultiLineComment("foo\nbar")).areEqual();
        Assert.assertThatCollections(new List<>("/*", "foo", "bar", "*/"), writer.writeMultiLineComment("\nfoo\nbar\n")).areEqual();

        Assert.assertThatCode(() -> writer.writeMultiLineComment("*/"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }

    private void testWriteDocumentationComment() {
        Assert.assertThatCollections(
            new List<>(
                "/**",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("")
        ).areEqual();

        Assert.assertThatCollections(
            new List<>(
                "/**",
                " *",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("\n")
        ).areEqual();

        Assert.assertThatCollections(
            new List<>(
                "/**",
                " *",
                " *",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("\n\n")
        ).areEqual();

        Assert.assertThatCollections(
            new List<>(
                "/**",
                " *  ",
                " */"
            ),
            writer.writeDocumentationComment(" ")
        ).areEqual();

        Assert.assertThatCollections(
            new List<>(
                "/**",
                " * \t",
                " */"
            ),
            writer.writeDocumentationComment("\t")
        ).areEqual();

        Assert.assertThatCollections(
            new List<>(
                "/**",
                " * foo bar",
                " */"
            ),
            writer.writeDocumentationComment("foo bar")
        ).areEqual();

        Assert.assertThatCollections(
            new List<>(
                "/**",
                " *",
                " * foo bar",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("\nfoo bar\n")
        ).areEqual();

        Assert.assertThatCollections(
            new List<>(
                "/**",
                " * foo",
                " * bar",
                " */"
            ),
            writer.writeDocumentationComment("foo\nbar")
        ).areEqual();

        Assert.assertThatCollections(
            new List<>(
                "/**",
                " *",
                " * foo",
                " * bar",
                " *",
                " */"
            ),
            writer.writeDocumentationComment("\nfoo\nbar\n")
        ).areEqual();

        Assert.assertThatCode(() -> writer.writeDocumentationComment("*/"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }
}
