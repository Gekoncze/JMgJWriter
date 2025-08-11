package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assert;
import cz.mg.token.Token;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;
import cz.mg.token.tokens.*;
import cz.mg.token.tokens.quotes.DoubleQuoteToken;
import cz.mg.token.tokens.quotes.SingleQuoteToken;

public @Test class TokenWritersTest {
    public static void main(String[] args) {
        System.out.print("Running " + TokenWritersTest.class.getSimpleName() + " ... ");

        TokenWritersTest test = new TokenWritersTest();
        test.testWriteUnsupported();
        test.testWriteWord();
        test.testWriteNumber();
        test.testWriteSymbol();
        test.testWriteWhitespace();
        test.testWriteDoubleQuote();
        test.testWriteSingleQuote();
        test.testWriteComment();
        test.testWriteBrackets();

        System.out.println("OK");
    }

    private final @Service TokenWriters writers = TokenWriters.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testWriteUnsupported() {
        Assert.assertThatCode(() -> writers.write(new Token("Hewwo", 0)))
            .withMessage("Unsupported token as input should throw writer exception.")
            .throwsException(WriterException.class);
    }

    private void testWriteWord() {
        Assert.assertEquals("Hewwo", writers.write(new WordToken("Hewwo", 0)));
    }

    private void testWriteNumber() {
        Assert.assertEquals("3.14f", writers.write(new NumberToken("3.14f", 0)));
    }

    private void testWriteSymbol() {
        Assert.assertEquals("+=", writers.write(new SymbolToken("+=", 0)));
    }

    private void testWriteWhitespace() {
        Assert.assertEquals("    ", writers.write(new WhitespaceToken("    ", 0)));
    }

    private void testWriteDoubleQuote() {
        Assert.assertEquals("\"foo\\\"bar\"", writers.write(new DoubleQuoteToken("foo\"bar", 0)));
    }

    private void testWriteSingleQuote() {
        Assert.assertEquals("'foo\\'bar'", writers.write(new SingleQuoteToken("foo'bar", 0)));
    }

    private void testWriteComment() {
        Assert.assertEquals("/*todo*/", writers.write(new CommentToken("todo", 0)));
    }

    private void testWriteBrackets() {
        Assert.assertEquals("{([hewwo])}", writers.write(
            b.curlyBrackets(
                b.roundBrackets(
                    b.squareBrackets(
                        t.word("hewwo")
                    )
                )
            )
        ));
    }
}
