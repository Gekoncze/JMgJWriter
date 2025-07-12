package cz.mg.java.writer.services.token;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.token.tokens.CommentToken;

public @Service class CommentTokenWriter {
    private static volatile @Service CommentTokenWriter instance;

    public static @Service CommentTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new CommentTokenWriter();
                }
            }
        }
        return instance;
    }

    private CommentTokenWriter() {
    }

    public @Mandatory String write(@Mandatory CommentToken token) {
        validate(token);
        return "/*" + removeNewLines(token.getText()) + "*/";
    }

    private void validate(@Mandatory CommentToken token) {
        if (token.getText().contains("*/")) {
            throw new WriterException(
                "Could not write comment token because it contains comment terminating sequence:\n"
                + token.getText()
            );
        }
    }

    private @Mandatory String removeNewLines(@Mandatory String text) {
        return text
            .replace("\r\n", " ")
            .replace("\n\r", " ")
            .replace("\n", " ")
            .replace("\r", " ");
    }
}
