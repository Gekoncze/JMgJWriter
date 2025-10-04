package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.code.formatter.Indentation;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JAnnotype;
import cz.mg.java.entities.JVariable;
import cz.mg.java.writer.services.tokens.ExpressionWriter;
import cz.mg.token.Token;

public @Service class JAnnotypeWriter implements JStructureWriter<JAnnotype> {
    private static volatile @Service JAnnotypeWriter instance;

    public static @Service JAnnotypeWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JAnnotypeWriter();
                    instance.classWriter = JClassWriter.getInstance();
                    instance.modifierWriter = JModifierWriter.getInstance();
                    instance.typeWriter = JTypeWriter.getInstance();
                    instance.expressionWriter = ExpressionWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JClassWriter classWriter;
    private @Service JModifierWriter modifierWriter;
    private @Service JTypeWriter typeWriter;
    private @Service ExpressionWriter expressionWriter;

    private JAnnotypeWriter() {
    }

    @Override
    public @Mandatory List<String> write(@Mandatory JAnnotype annotype) {
        List<String> lines = new List<>();
        lines.addCollectionLast(classWriter.writeComment(annotype.getComment()));
        lines.addCollectionLast(classWriter.writeAnnotations(annotype.getAnnotations()));
        lines.addCollectionLast(writeHeader(annotype));
        lines.addCollectionLast(writeBody(annotype));
        lines.addCollectionLast(classWriter.writeFooter());
        return lines;
    }

    private @Mandatory List<String> writeHeader(@Mandatory JAnnotype annotype) {
        String modifiers = classWriter.writeModifiers(annotype.getModifiers());
        String name = classWriter.writeName(annotype.getName());
        String header = classWriter.writeHeader(modifiers, "@interface", name);
        return new List<>(header + " {");
    }

    private @Mandatory List<String> writeBody(@Mandatory JAnnotype annotype) {
        List<String> lines = new List<>();
        for (JVariable element : annotype.getElements()) {
            lines.addLast(writeElement(element));
        }
        return Indentation.indent(lines);
    }

    private @Mandatory String writeElement(@Mandatory JVariable element) {
        String modifiers = modifierWriter.write(element.getModifiers());
        String type = typeWriter.write(element.getType());
        String name = element.getName() + "()";
        String expression = writeExpression(element.getExpression());
        return new StringJoiner<>(modifiers, type, name, expression)
            .withFilter(s -> !s.isBlank())
            .withDelimiter(" ")
            .join() + ";";
    }

    private @Mandatory String writeExpression(@Optional List<Token> expression) {
        return expression != null
            ? "default " + expressionWriter.write(expression)
            : "";
    }
}
