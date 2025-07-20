package cz.mg.java.writer.services.validators;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Value;
import cz.mg.java.entities.JCommentable;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.test.Assert;

public @Test class CommentValidatorTest {
    public static void main(String[] args) {
        System.out.print("Running " + CommentValidatorTest.class.getSimpleName() + " ... ");

        CommentValidatorTest test = new CommentValidatorTest();
        test.testValidateSingleLinePass();
        test.testValidateSingleLineFail();

        System.out.println("OK");
    }

    private final @Service CommentValidator validator = CommentValidator.getInstance();

    private void testValidateSingleLinePass() {
        Assert.assertThatCode(() -> validator.validateSingleLine(new JTest(null)))
            .withMessage("Null comment shall pass.")
            .doesNotThrowAnyException();

        Assert.assertThatCode(() -> validator.validateSingleLine(new JTest("")))
            .withMessage("Empty comment shall pass.")
            .doesNotThrowAnyException();

        Assert.assertThatCode(() -> validator.validateSingleLine(new JTest("foo bar")))
            .withMessage("This comment shall pass.")
            .doesNotThrowAnyException();
    }

    private void testValidateSingleLineFail() {
        Assert.assertThatCode(() -> validator.validateSingleLine(new JTest("foo\nbar")))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateSingleLine(new JTest("\nfoobar")))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateSingleLine(new JTest("foobar\n")))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);

        Assert.assertThatCode(() -> validator.validateSingleLine(new JTest("foo\rbar")))
            .withMessage("This comment shall not pass.")
            .throwsException(WriterException.class);
    }

    private static class JTest implements JCommentable {
        private String comment;

        public JTest(String comment) {
            this.comment = comment;
        }

        @Override
        @Optional @Value
        public String getComment() {
            return comment;
        }

        @Override
        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
