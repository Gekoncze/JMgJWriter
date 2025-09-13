package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.java.writer.services.tokens.brackets.CurlyBracketsWriter;
import cz.mg.test.Assertions;
import cz.mg.token.Token;
import cz.mg.token.test.BracketFactory;
import cz.mg.token.test.TokenFactory;
import cz.mg.token.tokens.NumberToken;
import cz.mg.token.tokens.SymbolToken;
import cz.mg.token.tokens.WhitespaceToken;
import cz.mg.token.tokens.WordToken;
import cz.mg.token.tokens.brackets.Brackets;
import cz.mg.token.tokens.comments.MultiLineCommentToken;
import cz.mg.token.tokens.comments.SingleLineCommentToken;
import cz.mg.token.tokens.quotes.DoubleQuoteToken;
import cz.mg.token.tokens.quotes.SingleQuoteToken;

public @Test class TokenWritersTest {
    public static void main(String[] args) {
        System.out.print("Running " + TokenWritersTest.class.getSimpleName() + " ... ");

        TokenWritersTest test = new TokenWritersTest();
        test.testGetUnsupported();
        test.testGetWord();
        test.testGetNumber();
        test.testGetSymbol();
        test.testGetWhitespace();
        test.testGetDoubleQuote();
        test.testGetSingleQuote();
        test.testGetSingleLineComment();
        test.testGetMultiLineComment();
        test.testGetBrackets();

        System.out.println("OK");
    }

    private final @Service TokenWriters writers = TokenWriters.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();
    private final @Service BracketFactory b = BracketFactory.getInstance();

    private void testGetUnsupported() {
        Assertions.assertThatCode(() -> writers.get(new Token("Hewwo", 0)))
            .withMessage("Writer exception should be thrown for unsupported token.")
            .throwsException(WriterException.class);
    }

    private void testGetWord() {
        Assertions.assertThat(writers.get(new WordToken("Hewwo", 0)))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(WordTokenWriter.class);
    }

    private void testGetNumber() {
        Assertions.assertThat(writers.get(new NumberToken("3.14f", 0)))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(NumberTokenWriter.class);
    }

    private void testGetSymbol() {
        Assertions.assertThat(writers.get(new SymbolToken("+=", 0)))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(SymbolTokenWriter.class);
    }

    private void testGetWhitespace() {
        Assertions.assertThat(writers.get(new WhitespaceToken("    ", 0)))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(WhitespaceTokenWriter.class);
    }

    private void testGetDoubleQuote() {
        Assertions.assertThat(writers.get(new DoubleQuoteToken("foo\"bar", 0)))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(DoubleQuoteTokenWriter.class);
    }

    private void testGetSingleQuote() {
        Assertions.assertThat(writers.get(new SingleQuoteToken("foo'bar", 0)))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(SingleQuoteTokenWriter.class);
    }

    private void testGetSingleLineComment() {
        Assertions.assertThat(writers.get(new SingleLineCommentToken("todo", 0)))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(SingleLineCommentTokenWriter.class);
    }

    private void testGetMultiLineComment() {
        Assertions.assertThat(writers.get(new MultiLineCommentToken("todo", 0)))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(MultiLineCommentTokenWriter.class);
    }

    private void testGetBrackets() {
        Brackets brackets = b.curlyBrackets(
            b.roundBrackets(
                b.squareBrackets(
                    t.word("hewwo")
                )
            )
        );

        Assertions.assertThat(writers.get(brackets))
            .withMessage("Wrong writer returned.")
            .isInstanceOf(CurlyBracketsWriter.class);
    }
}
