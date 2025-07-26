package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.java.entities.bounds.JBound;

public class JBoundsWriter {
    private static volatile @Service JBoundsWriter instance;

    public static @Service JBoundsWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JBoundsWriter();
                    instance.boundWriters = JBoundWriters.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JBoundWriters boundWriters;

    private JBoundsWriter() {
    }

    public @Mandatory String write(@Mandatory List<JBound> bounds) {
        return "<" + new StringJoiner<>(bounds)
            .withDelimiter(", ")
            .withConverter(bound -> boundWriters.write(bound))
            .join() + ">";
    }
}
