package cz.mg.java.writer.services.token.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.services.token.ExpressionWriter;
import cz.mg.token.tokens.brackets.RoundBrackets;

public @Service class RoundBracketsWriter {
    private static volatile @Service RoundBracketsWriter instance;

    public static @Service RoundBracketsWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new RoundBracketsWriter();
                    instance.expressionWriter = ExpressionWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service ExpressionWriter expressionWriter;

    private RoundBracketsWriter() {
    }

    public @Mandatory String write(@Mandatory RoundBrackets brackets) {
        return "(" + expressionWriter.write(brackets.getTokens()) + ")";
    }
}
