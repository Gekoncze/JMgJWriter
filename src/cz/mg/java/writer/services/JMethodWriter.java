package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JAnnotation;
import cz.mg.java.entities.JMethod;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.JVariable;
import cz.mg.java.entities.bounds.JBound;
import cz.mg.java.writer.services.bounds.JBoundsWriter;
import cz.mg.java.writer.services.formatting.Indentation;
import cz.mg.java.writer.services.tokens.ExpressionWriter;
import cz.mg.token.Token;

public @Service class JMethodWriter {
    private static final int LIMIT = 110;

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

    private JMethodWriter() {
    }

    public @Mandatory List<String> writeLines(@Mandatory JMethod method) {
        List<String> lines = new List<>();
        lines.addCollectionLast(writeComment(method.getComment()));
        lines.addCollectionLast(writeAnnotations(method.getAnnotations()));
        lines.addCollectionLast(writeHeader(method));
        writeImplementation(lines, method.getImplementation());
        return lines;
    }

    private @Mandatory List<String> writeComment(@Optional String comment) {
        if (comment != null) {
            return commentWriter.writeDocumentationComment(comment);
        } else {
            return new List<>();
        }
    }

    private @Mandatory List<String> writeAnnotations(@Mandatory List<JAnnotation> annotations) {
        List<String> lines = new List<>();
        for (JAnnotation annotation : annotations) {
            lines.addLast(annotationWriter.write(annotation));
        }
        return lines;
    }

    private @Mandatory List<String> writeHeader(@Mandatory JMethod method) {
        String modifiers = modifierWriter.write(method.getModifiers());
        String bounds = writeBounds(method.getBounds());
        String output = writeOutput(method.getOutput());
        String name = method.getName();

        String begin = new StringJoiner<>(modifiers, bounds, output, name)
            .withDelimiter(" ")
            .withFilter(s -> !s.isBlank())
            .join();

        List<String> input = writeInput(method.getInput());

        if (input.isEmpty() || estimateLineLength(begin, input) < LIMIT) {
            return writeHeaderWithParametersOnSingleLine(begin, input);
        } else {
            return writeHeaderWithParametersOnSeparateLines(begin, input);
        }
    }

    private @Mandatory String writeBounds(@Mandatory List<JBound> bounds) {
        return bounds.isEmpty() ? "" : boundsWriter.write(bounds);
    }

    private @Mandatory String writeOutput(@Optional JType type) {
        return type == null ? "void" : typeWriter.write(type);
    }

    private @Mandatory List<String> writeInput(@Mandatory List<JVariable> input) {
        List<String> parameters = new List<>();
        for (JVariable parameter : input) {
            parameters.addLast(variableWriter.write(parameter));
        }
        return parameters;
    }

    private int estimateLineLength(@Mandatory String begin, @Mandatory List<String> input) {
        int estimatedHeaderLength = Indentation.LENGTH + begin.length();
        for (String parameter : input) {
            estimatedHeaderLength += parameter.length();
        }
        estimatedHeaderLength += Math.max(0, 2 * (input.count() - 1)); // delimiters
        estimatedHeaderLength += 2 + 2; // brackets with space
        return estimatedHeaderLength;
    }

    private @Mandatory List<String> writeHeaderWithParametersOnSingleLine(
        @Mandatory String begin,
        @Mandatory List<String> input
    ) {
        String joinedInput = new StringJoiner<>(input)
            .withDelimiter(", ")
            .join();

        return new List<>(begin + "(" + joinedInput + ")");
    }

    private @Mandatory List<String> writeHeaderWithParametersOnSeparateLines(
        @Mandatory String begin,
        @Mandatory List<String> input
    ) {
        List<String> lines = new List<>(begin + "(");
        int i = 0;
        for (String parameter : input) {
            String delimiter = i < input.count() - 1 ? "," : "";
            lines.addLast("    " + parameter + delimiter);
            i++;
        }
        lines.addLast(")");
        return lines;
    }

    private void writeImplementation(@Mandatory List<String> lines, @Optional List<Token> implementation) {
        if (implementation != null) {
            // add curly brackets with implementation on separate line, to be improved later
            lines.getLastItem().set(lines.getLast() + " {");
            lines.addLast("    " + expressionWriter.write(implementation));
            lines.addLast("}");
        } else {
            // no implementation, so only add semicolon
            lines.getLastItem().set(lines.getLast() + ";");
        }
    }
}
