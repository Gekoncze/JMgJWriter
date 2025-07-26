package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.entities.JType;
import cz.mg.java.writer.services.bounds.JBoundsWriter;

public @Service class JTypeWriter {
    private static volatile @Service JTypeWriter instance;

    public static @Service JTypeWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JTypeWriter();
                    instance.boundsWriter = JBoundsWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JBoundsWriter boundsWriter;

    private JTypeWriter() {
    }

    public @Mandatory String write(@Mandatory JType type) {
        String bounds = type.getBounds() != null ? boundsWriter.write(type.getBounds()) : "";
        return type.getName() + bounds;
    }
}
