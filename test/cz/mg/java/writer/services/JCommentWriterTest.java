package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
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
        Assert.assertEquals("/**/", writer.writeMultiLineComment(""));
        Assert.assertEquals("/* */", writer.writeMultiLineComment(" "));
        Assert.assertEquals("/*\n*/", writer.writeMultiLineComment("\n"));
        Assert.assertEquals("/* foo bar */", writer.writeMultiLineComment("foo bar"));
        Assert.assertEquals("/* foo\nbar */", writer.writeMultiLineComment("foo\nbar"));
        Assert.assertEquals("/*\nfoo\nbar\n*/", writer.writeMultiLineComment("\nfoo\nbar\n"));

        Assert.assertThatCode(() -> writer.writeMultiLineComment("*/"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }

    private void testWriteDocumentationComment() {
        Assert.assertEquals(
            """
            /**
             *
             */
            """.trim(),
            writer.writeDocumentationComment("")
        );

        Assert.assertEquals(
            """
            /**
             *
             *
             */
            """.trim(),
            writer.writeDocumentationComment("\n"));

        Assert.assertEquals(
            """
            /**
             *
             *
             *
             */
            """.trim(),
            writer.writeDocumentationComment("\n\n"));

        Assert.assertEquals(
            """
           /**
            * \s
            */
           """.trim(),
            writer.writeDocumentationComment(" ")
        );

        Assert.assertEquals(
            """
            /**
             * \t
             */
            """.trim(),
            writer.writeDocumentationComment("\t")
        );

        Assert.assertEquals(
            """
            /**
             * foo bar
             */
            """.trim(),
            writer.writeDocumentationComment("foo bar")
        );

        Assert.assertEquals(
            """
            /**
             *
             * foo bar
             *
             */
            """.trim(),
            writer.writeDocumentationComment("\nfoo bar\n"));

        Assert.assertEquals(
            """
            /**
             * foo
             * bar
             */
            """.trim(),
            writer.writeDocumentationComment("foo\nbar"));

        Assert.assertEquals(
            """
            /**
             *
             * foo
             * bar
             *
             */
            """.trim(),
            writer.writeDocumentationComment("\nfoo\nbar\n"));

        Assert.assertThatCode(() -> writer.writeDocumentationComment("*/"))
            .withMessage("Missing comment validation.")
            .throwsException(WriterException.class);
    }
}
