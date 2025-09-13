package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.java.entities.*;
import cz.mg.java.entities.bounds.JBound;
import cz.mg.java.writer.components.LineMeasure;
import cz.mg.java.writer.components.LineMerger;
import cz.mg.java.writer.services.bounds.JBoundsWriter;
import cz.mg.java.writer.services.formatting.Indentation;
import cz.mg.java.writer.services.formatting.ListExpander;
import cz.mg.java.writer.services.tokens.ExpressionWriter;
import cz.mg.token.Token;

public @Service class JMethodWriter {
    private static volatile @Service JMethodWriter instance;

    public static @Service JMethodWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JMethodWriter();
                    instance.commentWriter = JCommentWriter.getInstance();
                    instance.annotationWriter = JAnnotationWriter.getInstance();
                    instance.modifierWriter = JModifierWriter.getInstance();
                    instance.boundsWriter = JBoundsWriter.getInstance();
                    instance.typeWriter = JTypeWriter.getInstance();
                    instance.variableWriter = JVariableWriter.getInstance();
                    instance.expressionWriter = ExpressionWriter.getInstance();
                    instance.listExpander = ListExpander.getInstance();
                    instance.indentation = Indentation.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JCommentWriter commentWriter;
    private @Service JAnnotationWriter annotationWriter;
    private @Service JModifierWriter modifierWriter;
    private @Service JBoundsWriter boundsWriter;
    private @Service JTypeWriter typeWriter;
    private @Service JVariableWriter variableWriter;
    private @Service ExpressionWriter expressionWriter;
    private @Service ListExpander listExpander;
    private @Service Indentation indentation;

    private JMethodWriter() {
    }

    public @Mandatory List<String> writeLines(@Mandatory JMethod method) {
        List<String> lines = new List<>();
        lines.addCollectionLast(writeComment(method.getComment()));
        lines.addCollectionLast(writeAnnotations(method.getAnnotations()));
        lines.addCollectionLast(writeCode(method));
        return lines;
    }

    @Mandatory List<String> writeComment(@Optional String comment) {
        if (comment != null) {
            return commentWriter.writeDocumentationComment(comment);
        } else {
            return new List<>();
        }
    }

    @Mandatory List<String> writeAnnotations(@Mandatory List<JAnnotation> annotations) {
        List<String> lines = new List<>();
        for (JAnnotation annotation : annotations) {
            lines.addLast(annotationWriter.write(annotation));
        }
        return lines;
    }

    private @Mandatory List<String> writeCode(@Mandatory JMethod method) {
        String modifiers = writeModifiers(method.getModifiers());
        String bounds = writeBounds(method.getBounds());
        String output = writeOutput(method.getOutput());
        String name = method.getName();
        String header = writeHeader(modifiers, bounds, output, name);
        List<String> parameters = writeParameters(header, method.getInput());
        List<String> implementation = writeImplementation(method.getImplementation());
        return new LineMerger()
            .merge(header)
            .merge(parameters)
            .merge(implementation)
            .get();
    }

    @Mandatory String writeModifiers(@Mandatory List<JModifier> modifiers) {
        return modifierWriter.write(modifiers);
    }

    @Mandatory String writeBounds(@Mandatory List<JBound> bounds) {
        return bounds.isEmpty() ? "" : boundsWriter.write(bounds);
    }

    private @Mandatory String writeOutput(@Optional JType type) {
        return type == null ? "void" : typeWriter.write(type);
    }

    @Mandatory String writeHeader(@Mandatory String... parts) {
        return new StringJoiner<>(parts)
            .withDelimiter(" ")
            .withFilter(s -> !s.isBlank())
            .join();
    }

    @Mandatory List<String> writeParameters(@Mandatory String header, @Mandatory List<JVariable> input) {
        List<String> parameters = writeInput(input);
        int estimatedLineLength = estimateLineLength(header, parameters);
        return listExpander.expand(parameters, estimatedLineLength);
    }

    private @Mandatory List<String> writeInput(@Mandatory List<JVariable> input) {
        List<String> parameters = new List<>();
        for (JVariable parameter : input) {
            parameters.addLast(variableWriter.write(parameter));
        }
        return parameters;
    }

    private int estimateLineLength(@Mandatory String header, @Mandatory List<String> parameters) {
        return new LineMeasure()
            .addIndentation(1)
            .addBrackets()
            .addPart(header)
            .addList(parameters)
            .length();
    }

    @Mandatory List<String> writeImplementation(@Optional List<Token> implementation) {
        if (implementation != null) {
            List<String> lines = new List<>();
            lines.addLast(" {");
            if (!implementation.isEmpty()) {
                lines.addCollectionLast(
                    indentation.indent(
                        expressionWriter.writeLines(implementation)
                    )
                );
            }
            lines.addLast("}");
            return lines;
        } else {
            return new List<>(";");
        }
    }
}
