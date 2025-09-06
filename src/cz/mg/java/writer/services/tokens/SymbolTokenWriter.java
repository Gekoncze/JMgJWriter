package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.tokens.SymbolToken;

public @Service class SymbolTokenWriter implements TokenWriter<SymbolToken> {
    private static volatile @Service SymbolTokenWriter instance;

    public static @Service SymbolTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SymbolTokenWriter();
                }
            }
        }
        return instance;
    }

    private SymbolTokenWriter() {
    }

    @Override
    public @Mandatory String write(@Mandatory SymbolToken token) {
        return token.getText();
    }
}
