package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.tokens.NumberToken;

public @Service class NumberTokenWriter implements TokenWriter<NumberToken> {
    private static volatile @Service NumberTokenWriter instance;

    public static @Service NumberTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new NumberTokenWriter();
                }
            }
        }
        return instance;
    }

    private NumberTokenWriter() {
    }

    @Override
    public @Mandatory String write(@Mandatory NumberToken token) {
        return token.getText();
    }
}
