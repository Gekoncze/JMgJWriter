package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.requirement.Required;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.java.entities.*;
import cz.mg.java.entities.bounds.JBound;
import cz.mg.java.writer.components.BlockBuilder;
import cz.mg.java.writer.services.bounds.JBoundsWriter;
import cz.mg.java.writer.services.formatting.Indentation;

public @Service class JClassWriter {
    private static volatile @Service JClassWriter instance;

    public static @Service JClassWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JClassWriter();
                    instance.commentWriter = JCommentWriter.getInstance();
                    instance.annotationWriter = JAnnotationWriter.getInstance();
                    instance.modifierWriter = JModifierWriter.getInstance();
                    instance.boundsWriter = JBoundsWriter.getInstance();
                    instance.typeWriter = JTypeWriter.getInstance();
                    instance.variableWriter = JVariableWriter.getInstance();
                    instance.constructorWriter = JConstructorWriter.getInstance();
                    instance.methodWriter = JMethodWriter.getInstance();
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
    private @Service JConstructorWriter constructorWriter;
    private @Service JMethodWriter methodWriter;
    private @Service Indentation indentation;

    private JClassWriter() {
    }

    public @Mandatory List<String> write(@Mandatory JClass jClass) {
        List<String> lines = new List<>();
        lines.addCollectionLast(writeComment(jClass.getComment()));
        lines.addCollectionLast(writeAnnotations(jClass.getAnnotations()));
        lines.addCollectionLast(writeHeader(jClass));
        lines.addCollectionLast(indentation.add(writeBody(jClass)));
        lines.addCollectionLast(writeFooter());
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

    private @Mandatory List<String> writeHeader(@Mandatory JClass jClass) {
        String modifiers = writeModifiers(jClass.getModifiers());
        String name = writeName(jClass.getName());
        String bounds = writeBounds(jClass.getBounds());
        String base = writeBase(jClass.getBase());
        String interfaces = writeInterfaces(jClass.getInterfaces());
        String header = writeHeader(modifiers, "class", name + bounds, base, interfaces);
        return new List<>(header + " {");
    }

    private @Mandatory String writeHeader(@Mandatory String... parts) {
        return new StringJoiner<>(parts)
            .withDelimiter(" ")
            .withFilter(s -> !s.isBlank())
            .join();
    }

    private @Mandatory String writeModifiers(@Mandatory List<JModifier> modifiers) {
        return modifierWriter.write(modifiers);
    }

    private @Mandatory String writeName(@Optional String name) {
        return name == null ? "" : name;
    }

    private @Mandatory String writeBounds(@Mandatory List<JBound> bounds) {
        return bounds.isEmpty() ? "" : boundsWriter.write(bounds);
    }

    private @Mandatory String writeBase(@Optional JType base) {
        return base == null ? "" : "extends " + typeWriter.write(base);
    }

    private @Mandatory String writeInterfaces(@Mandatory List<JType> interfaces) {
        return interfaces.isEmpty() ? "" : "implements " +  new StringJoiner<>(interfaces)
            .withDelimiter(", ")
            .withConverter(type -> typeWriter.write(type))
            .join();
    }

    private @Mandatory List<String> writeBody(@Mandatory JClass jClass) {
        BlockBuilder builder = new BlockBuilder();
        builder.addLines(writeFields(jClass.getFields()));
        builder.addLines(writeConstructors(jClass.getConstructors()));
        builder.addLines(writeMethods(jClass.getMethods()));
        return builder.build();
    }

    private @Mandatory List<String> writeFields(@Required List<JVariable> fields) {
        List<String> lines = new List<>();
        for (JVariable field : fields) {
            lines.addLast(variableWriter.write(field) + ";");
        }
        return lines;
    }

    private @Mandatory List<String> writeConstructors(@Required List<JConstructor> constructors) {
        BlockBuilder builder = new BlockBuilder();
        for (JConstructor constructor : constructors) {
            builder.addLines(constructorWriter.writeLines(constructor));
        }
        return builder.build();
    }

    private @Mandatory List<String> writeMethods(@Required List<JMethod> methods) {
        BlockBuilder builder = new BlockBuilder();
        for (JMethod method : methods) {
            builder.addLines(methodWriter.writeLines(method));
        }
        return builder.build();
    }

    private @Mandatory List<String> writeFooter() {
        return new List<>("}");
    }
}
