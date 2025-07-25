package cz.mg.java.writer.services.tokens.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.services.tokens.ExpressionWriter;
import cz.mg.token.tokens.brackets.SquareBrackets;

public @Service class SquareBracketsWriter {
    private static volatile @Service SquareBracketsWriter instance;

    public static @Service SquareBracketsWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SquareBracketsWriter();
                    instance.expressionWriter = ExpressionWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service ExpressionWriter expressionWriter;

    private SquareBracketsWriter() {
    }

    public @Mandatory String write(@Mandatory SquareBrackets brackets) {
        return "[" + expressionWriter.write(brackets.getTokens()) + "]";
    }
}
