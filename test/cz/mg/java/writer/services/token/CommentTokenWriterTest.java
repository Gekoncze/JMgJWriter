package cz.mg.java.writer.services.token;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.token.tokens.CommentToken;

import static cz.mg.test.Assert.assertEquals;
import static cz.mg.test.Assert.assertThatCode;

public @Test class CommentTokenWriterTest {
    public static void main(String[] args) {
        System.out.print("Running " + CommentTokenWriterTest.class.getSimpleName() + " ... ");

        CommentTokenWriterTest test = new CommentTokenWriterTest();
        test.testWriteEmpty();
        test.testWriteSimple();
        test.testWriteMultiLine();
        test.testWriteTerminatingSequence();

        System.out.println("OK");
    }

    private final @Service CommentTokenWriter writer = CommentTokenWriter.getInstance();

    private void testWriteEmpty() {
        String result = writer.write(new CommentToken("", -1));
        assertEquals("/**/", result);
    }

    private void testWriteSimple() {
        String result = writer.write(new CommentToken("foobar", -1));
        assertEquals("/*foobar*/", result);
    }

    private void testWriteMultiLine() {
        String result = writer.write(new CommentToken("foo\nbar\rfoobar\n\rbarfoo\r\n", -1));
        assertEquals("/*foo bar foobar barfoo */", result);
    }

    private void testWriteTerminatingSequence() {
        assertThatCode(() -> writer.write(new CommentToken("test*/", -1)))
            .withMessage("Terminating sequence should not be written.")
            .throwsException(WriterException.class);
    }
}
