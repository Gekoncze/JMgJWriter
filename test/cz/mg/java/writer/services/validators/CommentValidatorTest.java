package cz.mg.java.writer.services.validators;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assert;

public @Test class CommentValidatorTest {
    public static void main(String[] args) {
        System.out.print("Running " + CommentValidatorTest.class.getSimpleName() + " ... ");

        CommentValidatorTest test = new CommentValidatorTest();
        test.testValidateSingleLinePass();
        test.testValidateSingleLineFail();
        test.testValidateMultiLinePass();
        test.testValidateMultiLineFail();

        System.out.println("OK");
    }

    private final @Service CommentValidator validator = CommentValidator.getInstance();

    private void testValidateSingleLinePass() {
        Assert.assertThatCode(() -> validator.validateSingleLine(""))
            .withMessage("Empty comment shall pass.")
            .doesNotThrowAnyException();

        Assert.assertThatCode(() -> validator.validateSingleLine("foo bar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();
    }

    private void testValidateSingleLineFail() {
        Assert.assertThatCode(() -> validator.validateSingleLine("foo\nbar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateSingleLine("\nfoobar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateSingleLine("foobar\n"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateSingleLine("foo\rbar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);
    }

    private void testValidateMultiLinePass() {
        Assert.assertThatCode(() -> validator.validateMultiLine(""))
            .withMessage("Empty comment shall pass.")
            .doesNotThrowAnyException();

        Assert.assertThatCode(() -> validator.validateMultiLine("foo bar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assert.assertThatCode(() -> validator.validateMultiLine("foo\nbar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assert.assertThatCode(() -> validator.validateMultiLine("foo* /bar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assert.assertThatCode(() -> validator.validateMultiLine("foo/*bar"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assert.assertThatCode(() -> validator.validateMultiLine("* /"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();

        Assert.assertThatCode(() -> validator.validateMultiLine("/*"))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();
    }

    private void testValidateMultiLineFail() {
        Assert.assertThatCode(() -> validator.validateMultiLine("*/"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateMultiLine("foo*/bar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateMultiLine("foo bar*/"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateMultiLine("*/foo bar"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateMultiLine("foo\nbar*/"))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);
    }
}
