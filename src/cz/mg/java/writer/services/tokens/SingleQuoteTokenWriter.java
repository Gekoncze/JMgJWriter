package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.tokens.quotes.SingleQuoteToken;

public @Service class SingleQuoteTokenWriter implements TokenWriter<SingleQuoteToken> {
    private static volatile @Service SingleQuoteTokenWriter instance;

    public static @Service SingleQuoteTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SingleQuoteTokenWriter();
                }
            }
        }
        return instance;
    }

    private SingleQuoteTokenWriter() {
    }

    @Override
    public @Mandatory String write(@Mandatory SingleQuoteToken token) {
        return "'" + escapeSpecialCharacters(token.getText()) + "'";
    }

    private @Mandatory String escapeSpecialCharacters(@Mandatory String text) {
        return text.replace("\\", "\\\\")
            .replace("\t", "\\t")
            .replace("\b", "\\b")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("'", "\\'");
    }
}
