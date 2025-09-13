package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.token.tokens.CommentToken;
import cz.mg.token.tokens.comments.SingleLineCommentToken;

public @Service class SingleLineCommentTokenWriter implements TokenWriter<SingleLineCommentToken> {
    private static volatile @Service SingleLineCommentTokenWriter instance;

    public static @Service SingleLineCommentTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SingleLineCommentTokenWriter();
                }
            }
        }
        return instance;
    }

    private SingleLineCommentTokenWriter() {
    }

    @Override
    public @Mandatory String write(@Mandatory SingleLineCommentToken token) {
        validate(token);
        return "//" + token.getText();
    }

    private void validate(@Mandatory CommentToken token) {
        if (token.getText().contains("\n")) {
            throw new WriterException(
                "Could not write single line comment token because it contains new line."
            );
        }
    }
}
