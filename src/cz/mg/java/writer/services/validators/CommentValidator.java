package cz.mg.java.writer.services.validators;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.exceptions.WriterException;

public @Service class CommentValidator {
    private static volatile @Service CommentValidator instance;

    public static @Service CommentValidator getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new CommentValidator();
                }
            }
        }
        return instance;
    }

    private CommentValidator() {
    }

    public void validateSingleLine(@Mandatory String comment) {
        if (comment.contains("\n") || comment.contains("\r")) {
            throw new WriterException("New line is not allowed here.");
        }
    }

    public void validateMultiLine(@Mandatory String comment) {
        if (comment.contains("*/")) {
            throw new WriterException("Multi-line terminating sequence is not allowed here.");
        }
    }
}
