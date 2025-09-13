package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Required;
import cz.mg.annotations.storage.Parts;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JEnum;
import cz.mg.java.entities.JEnumEntry;
import cz.mg.java.writer.components.BlockBuilder;
import cz.mg.java.writer.services.formatting.Indentation;
import cz.mg.java.writer.services.tokens.ExpressionWriter;
import cz.mg.token.Token;

public @Service class JEnumWriter implements JStructureWriter<JEnum> {
    private static volatile @Service JEnumWriter instance;

    public static @Service JEnumWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JEnumWriter();
                    instance.classWriter = JClassWriter.getInstance();
                    instance.indentation = Indentation.getInstance();
                    instance.expressionWriter = ExpressionWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JClassWriter classWriter;
    private @Service Indentation indentation;
    private @Service ExpressionWriter expressionWriter;

    private JEnumWriter() {
    }

    @Override
    public @Mandatory List<String> write(@Mandatory JEnum jEnum) {
        List<String> lines = new List<>();
        lines.addCollectionLast(classWriter.writeComment(jEnum.getComment()));
        lines.addCollectionLast(classWriter.writeAnnotations(jEnum.getAnnotations()));
        lines.addCollectionLast(writeHeader(jEnum));
        lines.addCollectionLast(indentation.indent(writeBody(jEnum)));
        lines.addCollectionLast(classWriter.writeFooter());
        return lines;
    }

    private @Mandatory List<String> writeHeader(@Mandatory JEnum jEnum) {
        String modifiers = classWriter.writeModifiers(jEnum.getModifiers());
        String name = classWriter.writeName(jEnum.getName());
        String interfaces = classWriter.writeInterfaces(jEnum.getInterfaces(), "implements");
        String header = classWriter.writeHeader(modifiers, "enum", name, interfaces);
        return new List<>(header + " {");
    }

    private @Mandatory List<String> writeBody(@Mandatory JEnum jEnum) {
        BlockBuilder builder = new BlockBuilder();
        builder.addLines(writeEnumEntries(jEnum.getEntries()));
        builder.addLines(classWriter.writeFields(jEnum.getFields()));
        builder.addLines(classWriter.writeInitializers(jEnum.getInitializers()));
        builder.addLines(classWriter.writeConstructors(jEnum.getConstructors()));
        builder.addLines(classWriter.writeMethods(jEnum.getMethods()));
        builder.addLines(classWriter.writeInnerStructures(jEnum.getStructures()));
        return builder.build();
    }

    private @Mandatory List<String> writeEnumEntries(@Required @Parts List<JEnumEntry> entries) {
        List<String> lines = new List<>();
        int i = 0;
        for (JEnumEntry entry : entries) {
            String delimiter = i < entries.count() - 1 ? "," : ";";
            String line = entry.getExpression() != null
                ? entry.getName() + writeExpression(entry.getExpression()) + delimiter
                : entry.getName() + delimiter;
            lines.addLast(line);
            i++;
        }
        return lines;
    }

    private @Mandatory String writeExpression(@Mandatory List<Token> expression) {
        return "(" + expressionWriter.write(expression) + ")";
    }
}
