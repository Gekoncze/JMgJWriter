package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.entities.*;

public @Service class JStructureWriters {
    private static volatile @Service JStructureWriters instance;

    public static @Service JStructureWriters getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JStructureWriters();
                    instance.classWriter = JClassWriter.getInstance();
                    instance.interfaceWriter = JInterfaceWriter.getInstance();
                    instance.enumWriter = JEnumWriter.getInstance();
                    instance.recordWriter = JRecordWriter.getInstance();
                    instance.annotypeWriter = JAnnotypeWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JClassWriter classWriter;
    private @Service JInterfaceWriter interfaceWriter;
    private @Service JEnumWriter enumWriter;
    private @Service JRecordWriter recordWriter;
    private @Service JAnnotypeWriter annotypeWriter;

    private JStructureWriters() {
    }

    @SuppressWarnings("rawtypes")
    public @Mandatory JStructureWriter<JStructure> get(@Mandatory JStructure structure) {
        return switch (structure) {
            case JClass ignored -> (JStructureWriter) classWriter;
            case JInterface ignored -> (JStructureWriter) interfaceWriter;
            case JEnum ignored -> (JStructureWriter) enumWriter;
            case JRecord ignored -> (JStructureWriter) recordWriter;
            case JAnnotype ignored -> (JStructureWriter) annotypeWriter;
            default -> throw new UnsupportedOperationException(
                "Unsupported structure of type " + structure.getClass().getSimpleName() + "."
            );
        };
    }
}
