package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.List;
import cz.mg.file.page.Page;
import cz.mg.java.entities.JFile;
import cz.mg.java.entities.JImport;
import cz.mg.java.entities.JPackageLine;
import cz.mg.java.entities.JStructure;
import cz.mg.java.writer.components.BlockBuilder;

public @Service class JFileWriter {
    private static volatile @Service JFileWriter instance;

    public static @Service JFileWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JFileWriter();
                    instance.commentWriter = JCommentWriter.getInstance();
                    instance.packageLineWriter = JPackageLineWriter.getInstance();
                    instance.importWriter = JImportWriter.getInstance();
                    instance.structureWriters = JStructureWriters.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service JCommentWriter commentWriter;
    private @Service JPackageLineWriter packageLineWriter;
    private @Service JImportWriter importWriter;
    private @Service JStructureWriters structureWriters;

    private JFileWriter() {
    }

    public @Mandatory Page write(@Mandatory JFile file) {
        return new Page(
            file.getPath(),
            new BlockBuilder()
                .addLines(writeComment(file.getComment()))
                .addLine(writePackageLine(file.getPackageLine()))
                .addLines(writeImports(file.getImports()))
                .addLines(writeStructure(file.getStructure()))
                .build()
        );
    }

    private @Mandatory List<String> writeComment(@Optional String comment) {
        return comment != null
            ? commentWriter.writeDocumentationComment(comment)
            : new List<>();
    }

    private @Optional String writePackageLine(@Optional JPackageLine packageLine) {
        return packageLine != null
            ? packageLineWriter.write(packageLine)
            : null;
    }

    private @Mandatory List<String> writeImports(@Mandatory List<JImport> imports) {
        List<String> lines = new List<>();
        for (JImport jImport : imports) {
            lines.addLast(importWriter.write(jImport));
        }
        return lines;
    }

    private @Mandatory List<String> writeStructure(@Mandatory JStructure structure) {
        return structureWriters.write(structure);
    }
}
