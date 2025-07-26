package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.entities.bounds.JLowerBound;
import cz.mg.java.writer.services.JTypeWriter;

public @Service class JLowerBoundWriter {
    private static volatile @Service JLowerBoundWriter instance;

    public static @Service JLowerBoundWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JLowerBoundWriter();
                    instance.typeWriter = JTypeWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JTypeWriter typeWriter;

    private JLowerBoundWriter() {
    }

    public @Mandatory String write(@Mandatory JLowerBound bound) {
        return "? super " + typeWriter.write(bound.getType());
    }
}
