package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.java.writer.services.validators.CommentValidator;

public @Service class JCommentWriter {
    private static volatile @Service JCommentWriter instance;

    public static @Service JCommentWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JCommentWriter();
                    instance.commentValidator = CommentValidator.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service CommentValidator commentValidator;

    private JCommentWriter() {
    }

    public @Mandatory String writeSingleLineComment(@Mandatory String comment) {
        commentValidator.validateSingleLine(comment);
        String space = getStartingSpace(comment);
        return "//" + space + comment;
    }

    public @Mandatory String writeSingleLineStarComment(@Mandatory String comment) {
        commentValidator.validateSingleLine(comment);
        commentValidator.validateMultiLine(comment);
        String startingSpace = getStartingSpace(comment);
        String endingSpace = getEndingSpace(comment);
        return "/*" + startingSpace + comment + endingSpace + "*/";
    }

    public @Mandatory List<String> writeMultiLineComment(@Mandatory String comment) {
        commentValidator.validateMultiLine(comment);
        String startingSpace = getStartingSpace(comment);
        String endingSpace = getEndingSpace(comment);
        return split("/*" + startingSpace + comment + endingSpace + "*/");
    }

    public @Mandatory List<String> writeDocumentationComment(@Mandatory String comment) {
        commentValidator.validateMultiLine(comment);
        return split("/**\n" + reformatDocumentationContent(comment) + "\n */");
    }

    private @Mandatory String reformatDocumentationContent(@Mandatory String comment) {
        List<String> lines = split(comment);
        for (ListItem<String> item = lines.getFirstItem(); item != null; item = item.getNextItem()) {
            String line = item.get();
            String space = line.isEmpty() ? "" : " ";
            item.set(" *" + space + line);
        }
        return new StringJoiner<>(lines)
            .withDelimiter("\n")
            .join();
    }

    private @Mandatory List<String> split(@Mandatory String comment) {
        List<String> lines = new List<>();
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < comment.length(); i++) {
            char ch = comment.charAt(i);
            if (ch == '\n') {
                lines.addLast(line.toString());
                line = new StringBuilder();
            } else {
                line.append(ch);
            }
        }
        lines.addLast(line.toString());
        return lines;
    }

    private @Mandatory String getStartingSpace(@Mandatory String s) {
        return startsWithWhiteSpace(s) ? "" : " ";
    }

    private @Mandatory String getEndingSpace(@Mandatory String s) {
        return endsWithWhiteSpace(s) ? "" : " ";
    }

    private boolean startsWithWhiteSpace(@Mandatory String s) {
        return s.isEmpty() || s.isBlank() || Character.isWhitespace(s.charAt(0));
    }

    private boolean endsWithWhiteSpace(@Mandatory String s) {
        return s.isEmpty() || s.isBlank() || Character.isWhitespace(s.charAt(s.length() - 1));
    }
}
