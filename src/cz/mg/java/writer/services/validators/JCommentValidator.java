package cz.mg.java.writer.services.validators;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.exceptions.WriterException;

public @Service class JCommentValidator {
    private static volatile @Service JCommentValidator instance;

    public static @Service JCommentValidator getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JCommentValidator();
                }
            }
        }
        return instance;
    }

    private JCommentValidator() {
    }

    public void validateSingleLine(@Mandatory String comment) {
        if (comment.contains("\n") || comment.contains("\r")) {
            throw new WriterException("New line is not allowed in a single line comment.");
        }
    }

    public void validateMultiLine(@Mandatory String comment) {
        if (comment.contains("*/")) {
            throw new WriterException("Terminating sequence is not allowed in a multi-line comment.");
        }
    }
}
