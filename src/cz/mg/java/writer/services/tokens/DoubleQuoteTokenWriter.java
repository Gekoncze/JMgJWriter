package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.tokens.quotes.DoubleQuoteToken;

public @Service class DoubleQuoteTokenWriter implements TokenWriter<DoubleQuoteToken> {
    private static volatile @Service DoubleQuoteTokenWriter instance;

    public static @Service DoubleQuoteTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new DoubleQuoteTokenWriter();
                }
            }
        }
        return instance;
    }

    private DoubleQuoteTokenWriter() {
    }

    @Override
    public @Mandatory String write(@Mandatory DoubleQuoteToken token) {
        return '"' + escapeSpecialCharacters(token.getText()) + '"';
    }

    private @Mandatory String escapeSpecialCharacters(@Mandatory String text) {
        return text.replace("\\", "\\\\")
            .replace("\t", "\\t")
            .replace("\b", "\\b")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\"", "\\\"");
    }
}
