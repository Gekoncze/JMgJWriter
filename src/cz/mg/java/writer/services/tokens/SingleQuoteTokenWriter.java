package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.components.Escape;
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
        return "'" + escape(token.getText()) + "'";
    }

    private @Mandatory String escape(@Mandatory String text) {
        return new Escape(text)
            .backslash()
            .backspace()
            .singleQuote()
            .newline()
            .carriageReturn()
            .tab()
            .get();
    }
}
