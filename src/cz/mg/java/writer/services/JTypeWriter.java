package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JType;
import cz.mg.java.entities.bounds.JBound;
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
        String bounds = writeBounds(type.getBounds());
        String arrays = writeArrays(type.getDimensions());
        String varargs = writeVarargs(type.isVarargs());
        return type.getName() + bounds + arrays + varargs;
    }

    private @Mandatory String writeBounds(List<JBound> bounds) {
        return bounds != null ? boundsWriter.write(bounds) : "";
    }

    private @Mandatory String writeArrays(int dimensions) {
        return "[]".repeat(dimensions);
    }

    private @Mandatory String writeVarargs(boolean varargs) {
        return varargs ? "..." : "";
    }
}
