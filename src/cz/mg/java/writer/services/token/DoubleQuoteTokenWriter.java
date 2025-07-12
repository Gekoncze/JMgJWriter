package cz.mg.java.writer.services.token;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.token.tokens.quote.DoubleQuoteToken;

public @Service class DoubleQuoteTokenWriter {
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

    public @Mandatory String write(@Mandatory DoubleQuoteToken token) {
        validate(token);
        return '"' + removeNewLines(token.getText()) + '"';
    }

    private void validate(@Mandatory DoubleQuoteToken token) {
        if (token.getText().contains('"' + "")) {
            throw new WriterException(
                "Could not write quote token because it contains terminating character:\n"
                + token.getText()
            );
        }
    }

    private @Mandatory String removeNewLines(@Mandatory String text) {
        return text
            .replace("\r\n", " ")
            .replace("\n\r", " ")
            .replace("\n", " ")
            .replace("\r", " ");
    }
}
