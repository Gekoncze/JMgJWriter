package cz.mg.java.writer.services.tokens.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.writer.components.LineMerger;
import cz.mg.java.writer.services.tokens.ExpressionWriter;
import cz.mg.token.tokens.brackets.RoundBrackets;

public @Service class RoundBracketsWriter implements BracketsWriter<RoundBrackets> {
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

    @Override
    public @Mandatory String write(@Mandatory RoundBrackets brackets) {
        return "(" + expressionWriter.write(brackets.getTokens()) + ")";
    }

    @Override
    public @Mandatory List<String> writeLines(@Mandatory RoundBrackets brackets) {
        return new LineMerger()
            .merge("(")
            .merge(expressionWriter.writeLines(brackets.getTokens()))
            .merge(")")
            .get();
    }
}
