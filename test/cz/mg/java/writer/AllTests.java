package cz.mg.java.writer;

import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.components.BlockBuilderTest;
import cz.mg.java.writer.services.JAnnotationWriterTest;
import cz.mg.java.writer.services.JImportWriterTest;
import cz.mg.java.writer.services.JPackageLineWriterTest;
import cz.mg.java.writer.services.JTypeWriterTest;
import cz.mg.java.writer.services.bounds.*;
import cz.mg.java.writer.services.token.*;
import cz.mg.java.writer.services.token.brackets.BracketsWritersTest;
import cz.mg.java.writer.services.token.brackets.CurlyBracketsWriterTest;
import cz.mg.java.writer.services.token.brackets.RoundBracketsWriterTest;
import cz.mg.java.writer.services.token.brackets.SquareBracketsWriterTest;
import cz.mg.java.writer.services.validators.CommentValidatorTest;

public @Test class AllTests {
    public static void main(String[] args) {
        // cz.mg.java.writer.components
        BlockBuilderTest.main(args);

        // cz.mg.java.writer.services.bounds
        JBoundsWriterTest.main(args);
        JBoundWritersTest.main(args);
        JLowerBoundWriterTest.main(args);
        JTypeBoundWriterTest.main(args);
        JUnBoundWriterTest.main(args);
        JUpperBoundWriterTest.main(args);

        // cz.mg.java.writer.services.token
        CommentTokenWriterTest.main(args);
        DoubleQuoteTokenWriterTest.main(args);
        ExpressionWriterTest.main(args);
        NumberTokenWriterTest.main(args);
        SingleQuoteTokenWriterTest.main(args);
        SymbolTokenWriterTest.main(args);
        TokenWritersTest.main(args);
        WhitespaceTokenWriterTest.main(args);
        WordTokenWriterTest.main(args);

        // cz.mg.java.writer.services.token.brackets
        BracketsWritersTest.main(args);
        CurlyBracketsWriterTest.main(args);
        RoundBracketsWriterTest.main(args);
        SquareBracketsWriterTest.main(args);

        // cz.mg.java.writer.services.validators
        CommentValidatorTest.main(args);

        // cz.mg.java.writer.services
        JAnnotationWriterTest.main(args);
        JImportWriterTest.main(args);
        JPackageLineWriterTest.main(args);
        JTypeWriterTest.main(args);
    }
}
