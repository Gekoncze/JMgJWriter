package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.StringJoiner;
import cz.mg.java.entities.JPackageLine;
import cz.mg.java.writer.services.validators.CommentValidator;

public @Service class JPackageLineWriter {
    private static volatile @Service JPackageLineWriter instance;

    public static @Service JPackageLineWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JPackageLineWriter();
                    instance.commentValidator = CommentValidator.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service CommentValidator commentValidator;

    private JPackageLineWriter() {
    }

    public @Mandatory String write(@Mandatory JPackageLine packageLine) {
        commentValidator.validateSingleLine(packageLine);

        String path = new StringJoiner<>(packageLine.getPath())
            .withDelimiter(".")
            .join();

        String comment = packageLine.getComment() == null ? "" : " // " + packageLine.getComment();

        return "package " + path + ";" + comment;
    }
}
