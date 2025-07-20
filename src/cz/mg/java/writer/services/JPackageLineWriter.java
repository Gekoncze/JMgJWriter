package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.StringJoiner;
import cz.mg.java.entities.JPackageLine;

public @Service class JPackageLineWriter {
    private static volatile @Service JPackageLineWriter instance;

    public static @Service JPackageLineWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JPackageLineWriter();
                }
            }
        }
        return instance;
    }

    private JPackageLineWriter() {
    }

    public @Mandatory String write(@Mandatory JPackageLine packageLine) {
        String path = new StringJoiner<>(packageLine.getPath())
            .withDelimiter(".")
            .join();

        String comment = packageLine.getComment() == null ? "" : " // " + packageLine.getComment();

        return "package " + path + ";" + comment;
    }
}
