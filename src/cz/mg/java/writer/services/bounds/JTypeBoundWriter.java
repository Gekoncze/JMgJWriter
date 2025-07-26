package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.entities.bounds.JTypeBound;
import cz.mg.java.writer.services.JTypeWriter;

public @Service class JTypeBoundWriter {
    private static volatile @Service JTypeBoundWriter instance;

    public static @Service JTypeBoundWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JTypeBoundWriter();
                    instance.typeWriter = JTypeWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JTypeWriter typeWriter;

    private JTypeBoundWriter() {
    }

    public @Mandatory String write(@Mandatory JTypeBound bound) {
        return typeWriter.write(bound.getType());
    }
}
