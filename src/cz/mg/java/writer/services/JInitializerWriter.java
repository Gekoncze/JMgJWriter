package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JInitializer;
import cz.mg.java.writer.services.formatting.Indentation;
import cz.mg.java.writer.services.tokens.ExpressionWriter;

public @Service class JInitializerWriter {
    private static volatile @Service JInitializerWriter instance;

    public static @Service JInitializerWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JInitializerWriter();
                    instance.expressionWriter = ExpressionWriter.getInstance();
                    instance.indentation = Indentation.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service ExpressionWriter expressionWriter;
    private @Service Indentation indentation;

    private JInitializerWriter() {
    }

    public @Mandatory List<String> write(@Mandatory JInitializer initializer) {
        List<String> lines = new List<>();
        lines.addLast("static {");
        lines.addCollectionLast(
            indentation.indent(
                expressionWriter.writeLines(
                    initializer.getImplementation()
                )
            )
        );
        lines.addLast("}");
        return lines;
    }
}
