package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JPackageLine;

public @Service class JPackageLineWriter {
    private static volatile @Service JPackageLineWriter instance;

    public static @Service JPackageLineWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JPackageLineWriter();
                    instance.commentWriter = JCommentWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JCommentWriter commentWriter;

    private JPackageLineWriter() {
    }

    public @Mandatory String write(@Mandatory JPackageLine packageLine) {
        String path = writePath(packageLine.getPath());
        String comment = writeComment(packageLine.getComment());
        return "package " + path + ";" + comment;
    }

    private @Mandatory String writePath(@Mandatory List<String> path) {
        return new StringJoiner<>(path)
            .withDelimiter(".")
            .join();
    }

    private @Mandatory String writeComment(@Optional String comment) {
        return comment == null ? "" : " " + commentWriter.writeSingleLineComment(comment);
    }
}
