package cz.mg.java.writer.services.bounds;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.entities.bounds.*;
import cz.mg.java.writer.exceptions.WriterException;

public class JBoundWriters {
    private static volatile @Service JBoundWriters instance;

    public static @Service JBoundWriters getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JBoundWriters();
                    instance.unBoundWriter = JUnBoundWriter.getInstance();
                    instance.typeBoundWriter = JTypeBoundWriter.getInstance();
                    instance.upperBoundWriter = JUpperBoundWriter.getInstance();
                    instance.lowerBoundWriter = JLowerBoundWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JUnBoundWriter unBoundWriter;
    private @Service JTypeBoundWriter typeBoundWriter;
    private @Service JUpperBoundWriter upperBoundWriter;
    private @Service JLowerBoundWriter lowerBoundWriter;

    private JBoundWriters() {
    }

    public @Mandatory String write(@Mandatory JBound bound) {
        if (bound instanceof JUnBound unBound) {
            return unBoundWriter.write(unBound);
        }

        if (bound instanceof JTypeBound typeBound) {
            return typeBoundWriter.write(typeBound);
        }

        if (bound instanceof JUpperBound upperBound) {
            return upperBoundWriter.write(upperBound);
        }

        if (bound instanceof JLowerBound lowerBound) {
            return lowerBoundWriter.write(lowerBound);
        }

        throw new WriterException("Unsupported bound of type " + bound.getClass().getSimpleName() + ".");
    }
}
