package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JImport;

public @Service class JImportWriter {
    private static volatile @Service JImportWriter instance;

    public static @Service JImportWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JImportWriter();
                    instance.commentWriter = JCommentWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JCommentWriter commentWriter;

    private JImportWriter() {
    }

    public @Mandatory String write(@Mandatory JImport jImport) {
        String path = writePath(jImport.getPath());
        String comment = writeComment(jImport.getComment());
        return "import " + path + ";" + comment;
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
