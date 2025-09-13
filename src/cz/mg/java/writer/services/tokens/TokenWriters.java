package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.java.writer.services.tokens.brackets.BracketsWriters;
import cz.mg.token.Token;
import cz.mg.token.tokens.NumberToken;
import cz.mg.token.tokens.SymbolToken;
import cz.mg.token.tokens.WhitespaceToken;
import cz.mg.token.tokens.WordToken;
import cz.mg.token.tokens.brackets.Brackets;
import cz.mg.token.tokens.comments.MultiLineCommentToken;
import cz.mg.token.tokens.comments.SingleLineCommentToken;
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
                    instance.singleLineCommentTokenWriter = SingleLineCommentTokenWriter.getInstance();
                    instance.multiLineCommentTokenWriter = MultiLineCommentTokenWriter.getInstance();
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
    private @Service SingleLineCommentTokenWriter singleLineCommentTokenWriter;
    private @Service MultiLineCommentTokenWriter multiLineCommentTokenWriter;
    private @Service BracketsWriters bracketsWriters;

    private TokenWriters() {
    }

    @SuppressWarnings("rawtypes")
    public @Mandatory TokenWriter<Token> get(@Mandatory Token token) {
        return switch (token) {
            case WordToken ignored -> (TokenWriter) wordTokenWriter;
            case NumberToken ignored -> (TokenWriter) numberTokenWriter;
            case SymbolToken ignored -> (TokenWriter) symbolTokenWriter;
            case WhitespaceToken ignored -> (TokenWriter) whitespaceTokenWriter;
            case DoubleQuoteToken ignored -> (TokenWriter) doubleQuoteTokenWriter;
            case SingleQuoteToken ignored -> (TokenWriter) singleQuoteTokenWriter;
            case SingleLineCommentToken ignored -> (TokenWriter) singleLineCommentTokenWriter;
            case MultiLineCommentToken ignored -> (TokenWriter) multiLineCommentTokenWriter;
            case Brackets brackets -> (TokenWriter) bracketsWriters.get(brackets);
            default -> throw new WriterException(
                "Unsupported token of type " + token.getClass().getSimpleName() + "."
            );
        };
    }
}
