package cz.mg.java.writer.services.type;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.entities.types.JBaseType;

public @Service class JBaseTypeWriter {
    private static volatile @Service JBaseTypeWriter instance;

    public static @Service JBaseTypeWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JBaseTypeWriter();
                }
            }
        }
        return instance;
    }

    private JBaseTypeWriter() {
    }

    public @Mandatory String write(@Mandatory JBaseType type) {
        return type.getName();
    }
}
