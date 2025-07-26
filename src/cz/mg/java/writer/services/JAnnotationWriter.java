package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JAnnotation;
import cz.mg.java.writer.services.tokens.ExpressionWriter;
import cz.mg.token.Token;

public @Service class JAnnotationWriter {
    private static volatile @Service JAnnotationWriter instance;

    public static @Service JAnnotationWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JAnnotationWriter();
                    instance.expressionWriter = ExpressionWriter.getInstance();
                    instance.commentWriter = JCommentWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service ExpressionWriter expressionWriter;
    private @Service JCommentWriter commentWriter;

    private JAnnotationWriter() {
    }

    public @Mandatory String write(@Mandatory JAnnotation annotation) {
        String header = '@' + annotation.getName();
        String expression = writeExpression(annotation.getExpression());
        String comment = writeComment(annotation.getComment());
        return header + expression + comment;
    }

    private @Mandatory String writeExpression(@Optional List<Token> expression) {
        return expression == null ? "" : "(" + expressionWriter.write(expression) + ")";
    }

    private @Mandatory String writeComment(@Optional String comment) {
        return comment == null ? "" : " " + commentWriter.writeSingleLineStarComment(comment);
    }
}
