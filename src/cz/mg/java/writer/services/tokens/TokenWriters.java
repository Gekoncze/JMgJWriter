package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.java.writer.services.tokens.brackets.BracketsWriters;
import cz.mg.token.Token;
import cz.mg.token.tokens.*;
import cz.mg.token.tokens.brackets.Brackets;
import cz.mg.token.tokens.quotes.DoubleQuoteToken;
import cz.mg.token.tokens.quotes.SingleQuoteToken;

public @Service class TokenWriters {
    private static volatile @Service TokenWriters instance;

    public static @Service TokenWriters getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new TokenWriters();
                    instance.wordTokenWriter = WordTokenWriter.getInstance();
                    instance.numberTokenWriter = NumberTokenWriter.getInstance();
                    instance.symbolTokenWriter = SymbolTokenWriter.getInstance();
                    instance.whitespaceTokenWriter = WhitespaceTokenWriter.getInstance();
                    instance.doubleQuoteTokenWriter = DoubleQuoteTokenWriter.getInstance();
                    instance.singleQuoteTokenWriter = SingleQuoteTokenWriter.getInstance();
                    instance.commentTokenWriter = CommentTokenWriter.getInstance();
                    instance.bracketsWriters = BracketsWriters.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service WordTokenWriter wordTokenWriter;
    private @Service NumberTokenWriter numberTokenWriter;
    private @Service SymbolTokenWriter symbolTokenWriter;
    private @Service WhitespaceTokenWriter whitespaceTokenWriter;
    private @Service DoubleQuoteTokenWriter doubleQuoteTokenWriter;
    private @Service SingleQuoteTokenWriter singleQuoteTokenWriter;
    private @Service CommentTokenWriter commentTokenWriter;
    private @Service BracketsWriters bracketsWriters;

    private TokenWriters() {
    }

    public @Mandatory String write(@Mandatory Token token) {
        if (token instanceof WordToken wordToken) {
            return wordTokenWriter.write(wordToken);
        }

        if (token instanceof NumberToken numberToken) {
            return numberTokenWriter.write(numberToken);
        }

        if (token instanceof SymbolToken symbolToken) {
            return symbolTokenWriter.write(symbolToken);
        }

        if (token instanceof WhitespaceToken whitespaceToken) {
            return whitespaceTokenWriter.write(whitespaceToken);
        }

        if (token instanceof DoubleQuoteToken doubleQuoteToken) {
            return doubleQuoteTokenWriter.write(doubleQuoteToken);
        }

        if (token instanceof SingleQuoteToken singleQuoteToken) {
            return singleQuoteTokenWriter.write(singleQuoteToken);
        }

        if (token instanceof CommentToken commentToken) {
            return commentTokenWriter.write(commentToken);
        }

        if (token instanceof Brackets brackets) {
            return bracketsWriters.write(brackets);
        }

        throw new WriterException(
            "Unsupported token of type " + token.getClass().getSimpleName() + "."
        );
    }
}
