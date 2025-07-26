package cz.mg.java.writer.services.validators;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.entities.interfaces.JCommentable;
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

    public void validateSingleLine(@Mandatory JCommentable commentable) {
        String comment = commentable.getComment();
        if (comment != null && (comment.contains("\n") || comment.contains("\r"))) {
            throw new WriterException(
                "Multi line comments are not supported for " + commentable.getClass().getSimpleName() + "."
            );
        }
    }
}
