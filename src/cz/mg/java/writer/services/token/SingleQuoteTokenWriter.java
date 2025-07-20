package cz.mg.java.writer.services.token;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.tokens.quote.SingleQuoteToken;

public @Service class SingleQuoteTokenWriter {
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
