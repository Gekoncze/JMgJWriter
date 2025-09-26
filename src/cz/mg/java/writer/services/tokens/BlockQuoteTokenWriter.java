package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.writer.components.Escape;
import cz.mg.java.writer.components.LineMerger;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.token.tokens.quotes.BlockQuoteToken;

public @Service class BlockQuoteTokenWriter implements TokenWriter<BlockQuoteToken> {
    private static final String Q = '"' + "";

    private static volatile @Service BlockQuoteTokenWriter instance;

    public static @Service BlockQuoteTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new BlockQuoteTokenWriter();
                    instance.lineSeparator = LineSeparator.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service LineSeparator lineSeparator;

    private BlockQuoteTokenWriter() {
    }

    @Override
    public @Mandatory String write(@Mandatory BlockQuoteToken token) {
        throw new WriterException("Single line block quote tokens are not supported.");
    }

    @Override
    public @Mandatory List<String> writeLines(@Mandatory BlockQuoteToken token) {
        return new LineMerger()
            .merge(new List<>(Q + Q + Q, ""))
            .merge(lineSeparator.split(escape(token.getText())))
            .merge(Q + Q + Q)
            .get();
    }

    private @Mandatory String escape(@Mandatory String text) {
        return new Escape(text)
            .backslash()
            .backspace()
            .doubleQuote()
            .carriageReturn()
            .get();
    }
}
