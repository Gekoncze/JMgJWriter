package cz.mg.java.writer;

import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.components.BlockBuilderTest;
import cz.mg.java.writer.services.*;
import cz.mg.java.writer.services.bounds.*;
import cz.mg.java.writer.services.tokens.*;
import cz.mg.java.writer.services.tokens.brackets.BracketsWritersTest;
import cz.mg.java.writer.services.tokens.brackets.CurlyBracketsWriterTest;
import cz.mg.java.writer.services.tokens.brackets.RoundBracketsWriterTest;
import cz.mg.java.writer.services.tokens.brackets.SquareBracketsWriterTest;
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

        // cz.mg.java.writer.services.tokens
        CommentTokenWriterTest.main(args);
        DoubleQuoteTokenWriterTest.main(args);
        ExpressionWriterTest.main(args);
        NumberTokenWriterTest.main(args);
        SingleQuoteTokenWriterTest.main(args);
        SymbolTokenWriterTest.main(args);
        TokenWritersTest.main(args);
        WhitespaceTokenWriterTest.main(args);
        WordTokenWriterTest.main(args);

        // cz.mg.java.writer.services.tokens.brackets
        BracketsWritersTest.main(args);
        CurlyBracketsWriterTest.main(args);
        RoundBracketsWriterTest.main(args);
        SquareBracketsWriterTest.main(args);

        // cz.mg.java.writer.services.validators
        CommentValidatorTest.main(args);

        // cz.mg.java.writer.services
        JAnnotationWriterTest.main(args);
        JCommentWriterTest.main(args);
        JImportWriterTest.main(args);
        JPackageLineWriterTest.main(args);
        JTypeWriterTest.main(args);
    }
}
