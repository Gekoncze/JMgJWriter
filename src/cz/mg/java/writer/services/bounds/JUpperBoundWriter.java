package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.bounds.JUpperBound;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.java.writer.services.JTypeWriter;

public @Service class JUpperBoundWriter {
    private static volatile @Service JUpperBoundWriter instance;

    public static @Service JUpperBoundWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JUpperBoundWriter();
                    instance.typeWriter = JTypeWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JTypeWriter typeWriter;

    private JUpperBoundWriter() {
    }

    public @Mandatory String write(@Mandatory JUpperBound bound) {
        if (bound.getTypes().isEmpty()) {
            throw new WriterException("Missing upper bounds.");
        }

        return "? extends " + writeTypes(bound.getTypes());
    }

    private @Mandatory String writeTypes(@Mandatory List<JType> types) {
        return new StringJoiner<>(types)
            .withDelimiter(" & ")
            .withConverter(type -> typeWriter.write(type))
            .join();
    }
}
