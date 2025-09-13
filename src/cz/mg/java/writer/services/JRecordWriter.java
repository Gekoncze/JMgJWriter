package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JRecord;
import cz.mg.java.entities.JVariable;
import cz.mg.java.writer.components.BlockBuilder;
import cz.mg.java.writer.components.LineMeasure;
import cz.mg.java.writer.components.LineMerger;
import cz.mg.java.writer.services.formatting.Indentation;
import cz.mg.java.writer.services.formatting.ListExpander;

public @Service class JRecordWriter implements JStructureWriter<JRecord> {
    private static volatile @Service JRecordWriter instance;

    public static @Service JRecordWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JRecordWriter();
                    instance.classWriter = JClassWriter.getInstance();
                    instance.variableWriter = JVariableWriter.getInstance();
                    instance.indentation = Indentation.getInstance();
                    instance.listExpander = ListExpander.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JClassWriter classWriter;
    private @Service JVariableWriter variableWriter;
    private @Service Indentation indentation;
    private @Service ListExpander listExpander;

    private JRecordWriter() {
    }

    @Override
    public @Mandatory List<String> write(@Mandatory JRecord jRecord) {
        List<String> lines = new List<>();
        lines.addCollectionLast(classWriter.writeComment(jRecord.getComment()));
        lines.addCollectionLast(classWriter.writeAnnotations(jRecord.getAnnotations()));
        lines.addCollectionLast(writeHeader(jRecord));
        lines.addCollectionLast(indentation.indent(writeBody(jRecord)));
        lines.addCollectionLast(classWriter.writeFooter());
        return lines;
    }

    private @Mandatory List<String> writeHeader(@Mandatory JRecord jRecord) {
        String modifiers = classWriter.writeModifiers(jRecord.getModifiers());
        String name = classWriter.writeName(jRecord.getName());
        String bounds = classWriter.writeBounds(jRecord.getBounds());
        String leadingHeaderPart = classWriter.writeHeader(modifiers, "record", name + bounds);
        String interfaces = classWriter.writeInterfaces(jRecord.getInterfaces(), "implements");
        String trailingHeader = (interfaces.isEmpty() ? "" : " ") + interfaces + " {";
        List<String> properties = writeProperties(leadingHeaderPart, trailingHeader, jRecord.getProperties());
        return new LineMerger()
            .merge(leadingHeaderPart)
            .merge(properties)
            .merge(trailingHeader)
            .get();
    }

    private @Mandatory List<String> writeBody(@Mandatory JRecord jRecord) {
        BlockBuilder builder = new BlockBuilder();
        builder.addLines(classWriter.writeFields(jRecord.getFields()));
        builder.addLines(classWriter.writeInitializers(jRecord.getInitializers()));
        builder.addLines(classWriter.writeConstructors(jRecord.getConstructors()));
        builder.addLines(classWriter.writeMethods(jRecord.getMethods()));
        return builder.build();
    }

    private @Mandatory List<String> writeProperties(
        @Mandatory String leadingHeaderPart,
        @Mandatory String trailingHeaderPart,
        @Mandatory List<JVariable> properties
    ) {
        List<String> propertiesStrings = writePropertiesStrings(properties);
        int estimatedLineLength = estimateLineLength(leadingHeaderPart, trailingHeaderPart, propertiesStrings);
        return listExpander.expand(propertiesStrings, estimatedLineLength);
    }

    private @Mandatory List<String> writePropertiesStrings(@Mandatory List<JVariable> properties) {
        List<String> propertiesStrings = new List<>();
        for (JVariable property : properties) {
            propertiesStrings.addLast(variableWriter.write(property));
        }
        return propertiesStrings;
    }

    private int estimateLineLength(
        @Mandatory String leadingHeaderPart,
        @Mandatory String trailingHeaderPart,
        @Mandatory List<String> properties
    ) {
        return new LineMeasure()
            .addBrackets()
            .addPart(leadingHeaderPart)
            .addPart(trailingHeaderPart)
            .addList(properties)
            .length();
    }
}
