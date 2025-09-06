package cz.mg.java.writer.services.tokens.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.writer.components.LineMerger;
import cz.mg.java.writer.services.tokens.ExpressionWriter;
import cz.mg.token.tokens.brackets.CurlyBrackets;

public @Service class CurlyBracketsWriter implements BracketsWriter<CurlyBrackets> {
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

    @Override
    public @Mandatory String write(@Mandatory CurlyBrackets brackets) {
        return "{" + expressionWriter.write(brackets.getTokens()) + "}";
    }

    @Override
    public @Mandatory List<String> writeLines(@Mandatory CurlyBrackets brackets) {
        return new LineMerger()
            .merge(new List<>("{"))
            .merge(expressionWriter.writeLines(brackets.getTokens()))
            .merge(new List<>("}"))
            .get();
    }
}
