package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.code.formatter.BlockBuilder;
import cz.mg.code.formatter.Indentation;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JInterface;

public @Service class JInterfaceWriter implements JStructureWriter<JInterface> {
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

    @Override
    public @Mandatory List<String> write(@Mandatory JInterface jInterface) {
        List<String> lines = new List<>();
        lines.addCollectionLast(classWriter.writeComment(jInterface.getComment()));
        lines.addCollectionLast(classWriter.writeAnnotations(jInterface.getAnnotations()));
        lines.addCollectionLast(writeHeader(jInterface));
        lines.addCollectionLast(indentation.indent(writeBody(jInterface)));
        lines.addCollectionLast(classWriter.writeFooter());
        return lines;
    }

    private @Mandatory List<String> writeHeader(@Mandatory JInterface jInterface) {
        String modifiers = classWriter.writeModifiers(jInterface.getModifiers());
        String name = classWriter.writeName(jInterface.getName());
        String bounds = classWriter.writeBounds(jInterface.getBounds());
        String interfaces = classWriter.writeInterfaces(jInterface.getInterfaces(), "extends");
        String permits = classWriter.writePermits(jInterface.getPermits());
        String header = classWriter.writeHeader(modifiers, "interface", name + bounds, interfaces, permits);
        return new List<>(header + " {");
    }

    private @Mandatory List<String> writeBody(@Mandatory JInterface jInterface) {
        return new BlockBuilder()
            .addLines(classWriter.writeFields(jInterface.getFields()))
            .addLines(classWriter.writeMethods(jInterface.getMethods()))
            .addLines(classWriter.writeInnerStructures(jInterface.getStructures()))
            .build();
    }
}
