package cz.mg.java.writer;

import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.components.BlockBuilderTest;
import cz.mg.java.writer.services.JImportWriterTest;
import cz.mg.java.writer.services.JPackageLineWriterTest;
import cz.mg.java.writer.services.token.*;

public @Test class AllTests {
    public static void main(String[] args) {
        // cz.mg.java.writer.components
        BlockBuilderTest.main(args);

        // cz.mg.java.writer.services.token
        CommentTokenWriterTest.main(args);
        NumberTokenWriterTest.main(args);
        SymbolTokenWriterTest.main(args);
        WhitespaceTokenWriterTest.main(args);
        WordTokenWriterTest.main(args);

        // cz.mg.java.writer.services
        JImportWriterTest.main(args);
        JPackageLineWriterTest.main(args);
    }
}
