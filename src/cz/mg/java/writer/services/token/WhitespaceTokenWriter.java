package cz.mg.java.writer.services.token;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.tokens.WhitespaceToken;

public @Service class WhitespaceTokenWriter {
    private static volatile @Service WhitespaceTokenWriter instance;

    public static @Service WhitespaceTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new WhitespaceTokenWriter();
                }
            }
        }
        return instance;
    }

    private WhitespaceTokenWriter() {
    }

    public @Mandatory String write(@Mandatory WhitespaceToken token) {
        return removeNewLines(token.getText());
    }

    private @Mandatory String removeNewLines(@Mandatory String text) {
        return text
            .replace("\r\n", " ")
            .replace("\n\r", " ")
            .replace("\n", " ")
            .replace("\r", " ");
    }
}
