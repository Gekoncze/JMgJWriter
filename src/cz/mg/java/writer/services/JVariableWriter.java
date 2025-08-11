package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JAnnotation;
import cz.mg.java.entities.JVariable;
import cz.mg.java.writer.services.tokens.ExpressionWriter;
import cz.mg.token.Token;

public @Service class JVariableWriter {
    private static volatile @Service JVariableWriter instance;

    public static @Service JVariableWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JVariableWriter();
                    instance.annotationWriter = JAnnotationWriter.getInstance();
                    instance.typeWriter = JTypeWriter.getInstance();
                    instance.expressionWriter = ExpressionWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JAnnotationWriter annotationWriter;
    private @Service JTypeWriter typeWriter;
    private @Service ExpressionWriter expressionWriter;

    private JVariableWriter() {
    }

    public @Mandatory String write(@Mandatory JVariable variable) {
        String annotations = writeAnnotations(variable.getAnnotations());
        String type = typeWriter.write(variable.getType());
        String name = variable.getName();
        String expression = writeExpression(variable.getExpression());
        return annotations + type + " " + name + expression;
    }

    private @Mandatory String writeAnnotations(@Mandatory List<JAnnotation> annotations) {
        String endingSpace = annotations.isEmpty() ? "" : " ";
        return new StringJoiner<>(annotations)
            .withDelimiter(" ")
            .withConverter(annotation -> annotationWriter.write(annotation))
            .join() + endingSpace;
    }

    private @Mandatory String writeExpression(@Optional List<Token> expression) {
        return expression == null ? "" : " = " + expressionWriter.write(expression);
    }
}
