package cz.mg.java.writer.services.validators;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assertions;

public @Test class JCommentValidatorTest {
    public static void main(String[] args) {
        System.out.print("Running " + JCommentValidatorTest.class.getSimpleName() + " ... ");

        JCommentValidatorTest test = new JCommentValidatorTest();
        test.testValidateSingleLinePass();
        test.testValidateSingleLineFail();
        test.testValidateMultiLinePass();
        test.testValidateMultiLineFail();

        System.out.println("OK");
    }

    private final @Service JCommentValidator validator = JCommentValidator.getInstance();

    private void testValidateSingleLinePass() {
        Assertions.assertThatCode(() -> validator.validateSingleLine(""))
            .withMessage("Empty comment shall pass.")
            .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> validator.validateSingleLine("foo bar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();
    }

    private void testValidateSingleLineFail() {
        Assertions.assertThatCode(() -> validator.validateSingleLine("foo\nbar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> validator.validateSingleLine("\nfoobar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> validator.validateSingleLine("foobar\n"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> validator.validateSingleLine("foo\rbar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);
    }

    private void testValidateMultiLinePass() {
        Assertions.assertThatCode(() -> validator.validateMultiLine(""))
            .withMessage("Empty comment shall pass.")
            .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> validator.validateMultiLine("foo bar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> validator.validateMultiLine("foo\nbar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> validator.validateMultiLine("foo* /bar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> validator.validateMultiLine("foo/*bar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> validator.validateMultiLine("* /"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> validator.validateMultiLine("/*"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();
    }

    private void testValidateMultiLineFail() {
        Assertions.assertThatCode(() -> validator.validateMultiLine("*/"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> validator.validateMultiLine("foo*/bar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> validator.validateMultiLine("foo bar*/"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> validator.validateMultiLine("*/foo bar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assertions.assertThatCode(() -> validator.validateMultiLine("foo\nbar*/"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);
    }
}
