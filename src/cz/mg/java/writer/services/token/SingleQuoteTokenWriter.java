package cz.mg.java.writer.services.token;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.exceptions.WriterException;
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
        validate(token);
        return "'" + removeNewLines(token.getText()) + "'";
    }

    private void validate(@Mandatory SingleQuoteToken token) {
        if (token.getText().contains("'")) {
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
