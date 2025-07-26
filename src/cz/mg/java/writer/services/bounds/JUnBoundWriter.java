package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.entities.bounds.JUnBound;

public @Service class JUnBoundWriter {
    private static volatile @Service JUnBoundWriter instance;

    public static @Service JUnBoundWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JUnBoundWriter();
                }
            }
        }
        return instance;
    }

    private JUnBoundWriter() {
    }

    public @Mandatory String write(@Mandatory JUnBound bound) {
        return "?";
    }
}
