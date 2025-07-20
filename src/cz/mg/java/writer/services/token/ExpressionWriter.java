package cz.mg.java.writer.services.token;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.token.Token;

public @Service class ExpressionWriter {
    private static volatile @Service ExpressionWriter instance;

    public static @Service ExpressionWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new ExpressionWriter();
                    instance.tokenWriters = TokenWriters.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service TokenWriters tokenWriters;

    private ExpressionWriter() {
    }

    public @Mandatory String write(@Mandatory List<Token> expression) {
        StringBuilder result = new StringBuilder();
        for (Token token : expression) {
            result.append(tokenWriters.write(token));
        }
        return result.toString();
    }
}
