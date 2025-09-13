package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.java.writer.test.LineAssert;
import cz.mg.test.Assertions;
import cz.mg.token.test.TokenFactory;

import static cz.mg.test.Assert.assertEquals;

public @Test class MultiLineCommentTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + MultiLineCommentTokenWriterTest.class.getSimpleName() + " ... ");

        MultiLineCommentTokenWriterTest test = new MultiLineCommentTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteSimple();
        test.testWriteMultiLine();
        test.testWriteTerminatingSequence();
        test.testWriteLinesEmpty();
        test.testWriteLinesSimple();
        test.testWriteLinesMultiLine();
        test.testWriteLinesTerminatingSequence();

        System.out.println("OK");
    }

    private final @Service MultiLineCommentTokenWriter writer = MultiLineCommentTokenWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(t.multiLineComment(""));
        assertEquals("/**/", result);
    }

    private void testWriteSimple() {
        String result = writer.write(t.multiLineComment("foobar"));
        assertEquals("/*foobar*/", result);
    }

    private void testWriteMultiLine() {
        String result = writer.write(t.multiLineComment("foo\nbar\nfoobar\nbarfoo\n"));
        assertEquals("/*foo bar foobar barfoo */", result);
    }

    private void testWriteTerminatingSequence() {
        Assertions.assertThatCode(() -> writer.write(t.multiLineComment("*/")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> writer.write(t.multiLineComment("test*/")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> writer.write(t.multiLineComment("*/test")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);
    }

    private void testWriteLinesEmpty() {
        LineAssert.assertEquals(
            new List<>("/**/"),
            writer.writeLines(t.multiLineComment(""))
        );
    }

    private void testWriteLinesSimple() {
        LineAssert.assertEquals(
            new List<>(
                "/*foo",
                "bar*/"
            ),
            writer.writeLines(t.multiLineComment("foo\nbar"))
        );
    }

    private void testWriteLinesMultiLine() {
        LineAssert.assertEquals(
            new List<>(
                "/*foo",
                "bar",
                "foobar",
                "barfoo",
                "*/"
            ),
            writer.writeLines(t.multiLineComment("foo\nbar\nfoobar\nbarfoo\n"))
        );
    }

    private void testWriteLinesTerminatingSequence() {
        Assertions.assertThatCode(() -> writer.writeLines(t.multiLineComment("*/")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> writer.writeLines(t.multiLineComment("test*/")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> writer.writeLines(t.multiLineComment("*/test")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);
    }
}
