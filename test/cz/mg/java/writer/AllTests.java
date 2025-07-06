package cz.mg.java.writer;

import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.components.BlockBuilderTest;
import cz.mg.java.writer.services.JImportWriterTest;
import cz.mg.java.writer.services.JPackageLineWriterTest;

public @Test class AllTests {
    public static void main(String[] args) {
        // cz.mg.java.writer.components
        BlockBuilderTest.main(args);

        // cz.mg.java.writer.services
        JImportWriterTest.main(args);
        JPackageLineWriterTest.main(args);
    }
}
