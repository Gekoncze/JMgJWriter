package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JConstructor;
import cz.mg.java.writer.components.LineMerger;

public @Service class JConstructorWriter {
    private static volatile @Service JConstructorWriter instance;

    public static @Service JConstructorWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JConstructorWriter();
                    instance.methodWriter = JMethodWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private JMethodWriter methodWriter;

    private JConstructorWriter() {
    }

    public @Mandatory List<String> writeLines(@Mandatory JConstructor constructor) {
        List<String> lines = new List<>();
        lines.addCollectionLast(methodWriter.writeComment(constructor.getComment()));
        lines.addCollectionLast(methodWriter.writeAnnotations(constructor.getAnnotations()));
        lines.addCollectionLast(writeCode(constructor));
        return lines;
    }

    private @Mandatory List<String> writeCode(@Mandatory JConstructor constructor) {
        String modifiers = methodWriter.writeModifiers(constructor.getModifiers());
        String name = constructor.getName();
        String header = methodWriter.writeHeader(modifiers, name);
        List<String> parameters = methodWriter.writeParameters(header, constructor.getInput());
        List<String> implementation = methodWriter.writeImplementation(constructor.getImplementation());
        return new LineMerger()
            .merge(header)
            .merge(parameters)
            .merge(implementation)
            .get();
    }
}
