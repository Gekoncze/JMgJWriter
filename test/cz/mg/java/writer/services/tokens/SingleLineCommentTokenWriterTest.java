package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.code.formatter.test.LineAssert;
import cz.mg.collections.list.List;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assertions;
import cz.mg.token.test.TokenFactory;

import static cz.mg.test.Assert.assertEquals;

public @Test class SingleLineCommentTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + SingleLineCommentTokenWriterTest.class.getSimpleName() + " ... ");

        SingleLineCommentTokenWriterTest test = new SingleLineCommentTokenWriterTest();
        test.testWriteEmpty();
        test.testWrite();
        test.testWriteTerminatingSequence();
        test.testWriteLinesEmpty();
        test.testWriteLines();
        test.testWriteLinesTerminatingSequence();

        System.out.println("OK");
    }

    private final @Service SingleLineCommentTokenWriter writer = SingleLineCommentTokenWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(t.singleLineComment(""));
        assertEquals("//", result);
    }

    private void testWrite() {
        String result = writer.write(t.singleLineComment("foo"));
        assertEquals("//foo", result);
    }

    private void testWriteTerminatingSequence() {
        Assertions.assertThatCode(() -> writer.write(t.singleLineComment("\n")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> writer.write(t.singleLineComment("test\n")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> writer.write(t.singleLineComment("\ntest")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);
    }

    private void testWriteLinesEmpty() {
        LineAssert.assertEquals(
            new List<>("//"),
            writer.writeLines(t.singleLineComment(""))
        );
    }

    private void testWriteLines() {
        LineAssert.assertEquals(
            new List<>("//foo"),
            writer.writeLines(t.singleLineComment("foo"))
        );
    }

    private void testWriteLinesTerminatingSequence() {
        Assertions.assertThatCode(() -> writer.writeLines(t.singleLineComment("\n")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> writer.writeLines(t.singleLineComment("test\n")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> writer.writeLines(t.singleLineComment("\ntest")))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);
    }
}
