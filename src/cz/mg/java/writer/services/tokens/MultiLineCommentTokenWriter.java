package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.token.tokens.comments.MultiLineCommentToken;

public @Service class MultiLineCommentTokenWriter implements TokenWriter<MultiLineCommentToken> {
    private static volatile @Service MultiLineCommentTokenWriter instance;

    public static @Service MultiLineCommentTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new MultiLineCommentTokenWriter();
                }
            }
        }
        return instance;
    }

    private MultiLineCommentTokenWriter() {
    }

    @Override
    public @Mandatory String write(@Mandatory MultiLineCommentToken token) {
        validate(token);
        return "/*" + removeNewLines(token.getText()) + "*/";
    }

    @Override
    public @Mandatory List<String> writeLines(@Mandatory MultiLineCommentToken token) {
        validate(token);
        String text = token.getText();
        List<String> lines = new List<>();
        StringBuilder builder = new StringBuilder("/*");
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                lines.addLast(builder.toString());
                builder = new StringBuilder();
            } else {
                builder.append(ch);
            }
        }
        builder.append("*/");
        lines.addLast(builder.toString());
        return lines;
    }

    private void validate(@Mandatory MultiLineCommentToken token) {
        if (token.getText().contains("*/")) {
            throw new WriterException(
                "Could not write comment token because it contains comment terminating sequence:\n"
                + token.getText()
            );
        }
    }

    private @Mandatory String removeNewLines(@Mandatory String text) {
        return text.replace("\n", " ");
    }
}
