package cz.mg.java.writer.services.token.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.services.token.ExpressionWriter;
import cz.mg.token.tokens.brackets.CurlyBrackets;

public @Service class CurlyBracketsWriter {
    private static volatile @Service CurlyBracketsWriter instance;

    public static @Service CurlyBracketsWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new CurlyBracketsWriter();
                    instance.expressionWriter = ExpressionWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service ExpressionWriter expressionWriter;

    private CurlyBracketsWriter() {
    }

    public @Mandatory String write(@Mandatory CurlyBrackets brackets) {
        return "{" + expressionWriter.write(brackets.getTokens()) + "}";
    }
}
