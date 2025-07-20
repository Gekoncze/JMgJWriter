package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.StringJoiner;
import cz.mg.java.entities.JImport;
import cz.mg.java.writer.services.validators.CommentValidator;

public @Service class JImportWriter {
    private static volatile @Service JImportWriter instance;

    public static @Service JImportWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JImportWriter();
                    instance.commentValidator = CommentValidator.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service CommentValidator commentValidator;

    private JImportWriter() {
    }

    public @Mandatory String write(@Mandatory JImport jImport) {
        commentValidator.validateSingleLine(jImport);

        String path = new StringJoiner<>(jImport.getPath())
            .withDelimiter(".")
            .join();

        String comment = jImport.getComment() == null ? "" : " // " + jImport.getComment();

        return "import " + path + ";" + comment;
    }
}
