package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.tokens.WordToken;

public @Service class WordTokenWriter {
    private static volatile @Service WordTokenWriter instance;

    public static @Service WordTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new WordTokenWriter();
                }
            }
        }
        return instance;
    }

    private WordTokenWriter() {
    }

    public @Mandatory String write(@Mandatory WordToken token) {
        return token.getText();
    }
}
