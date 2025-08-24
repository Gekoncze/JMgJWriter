package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JInterface;
import cz.mg.java.writer.components.BlockBuilder;
import cz.mg.java.writer.services.formatting.Indentation;

public @Service class JInterfaceWriter {
    private static volatile @Service JInterfaceWriter instance;

    public static @Service JInterfaceWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JInterfaceWriter();
                    instance.classWriter = JClassWriter.getInstance();
                    instance.indentation = Indentation.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JClassWriter classWriter;
    private @Service Indentation indentation;

    private JInterfaceWriter() {
    }

    public @Mandatory List<String> write(@Mandatory JInterface jInterface) {
        List<String> lines = new List<>();
        lines.addCollectionLast(classWriter.writeComment(jInterface.getComment()));
        lines.addCollectionLast(classWriter.writeAnnotations(jInterface.getAnnotations()));
        lines.addCollectionLast(writeHeader(jInterface));
        lines.addCollectionLast(indentation.add(writeBody(jInterface)));
        lines.addCollectionLast(classWriter.writeFooter());
        return lines;
    }

    private @Mandatory List<String> writeHeader(@Mandatory JInterface jInterface) {
        String modifiers = classWriter.writeModifiers(jInterface.getModifiers());
        String name = classWriter.writeName(jInterface.getName());
        String bounds = classWriter.writeBounds(jInterface.getBounds());
        String interfaces = classWriter.writeInterfaces(jInterface.getInterfaces(), "extends");
        String header = classWriter.writeHeader(modifiers, "interface", name + bounds, interfaces);
        return new List<>(header + " {");
    }

    private @Mandatory List<String> writeBody(@Mandatory JInterface jInterface) {
        BlockBuilder builder = new BlockBuilder();
        builder.addLines(classWriter.writeFields(jInterface.getFields()));
        builder.addLines(classWriter.writeMethods(jInterface.getMethods()));
        return builder.build();
    }
}
