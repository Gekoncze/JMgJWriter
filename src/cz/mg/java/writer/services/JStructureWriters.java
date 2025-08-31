package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JClass;
import cz.mg.java.entities.JEnum;
import cz.mg.java.entities.JInterface;
import cz.mg.java.entities.JStructure;

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
                }
            }
        }
        return instance;
    }

    private @Service JClassWriter classWriter;
    private @Service JInterfaceWriter interfaceWriter;
    private @Service JEnumWriter enumWriter;

    private JStructureWriters() {
    }

    public @Mandatory List<String> write(@Mandatory JStructure structure) {
        return switch (structure) {
            case JClass jClass -> classWriter.write(jClass);
            case JInterface jInterface -> interfaceWriter.write(jInterface);
            case JEnum jEnum -> enumWriter.write(jEnum);
            default -> throw new UnsupportedOperationException(
                "Unsupported structure of type " + structure.getClass().getSimpleName() + "."
            );
        };
    }
}
