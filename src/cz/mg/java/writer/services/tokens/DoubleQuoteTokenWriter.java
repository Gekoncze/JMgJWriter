package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.components.Escape;
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
        return '"' + escape(token.getText()) + '"';
    }

    private @Mandatory String escape(@Mandatory String text) {
        return new Escape(text)
            .backslash()
            .backspace()
            .doubleQuote()
            .newline()
            .carriageReturn()
            .tab()
            .get();
    }
}
