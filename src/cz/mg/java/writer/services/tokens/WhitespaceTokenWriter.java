package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.token.tokens.WhitespaceToken;

public @Service class WhitespaceTokenWriter {
    private static volatile @Service WhitespaceTokenWriter instance;

    public static @Service WhitespaceTokenWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new WhitespaceTokenWriter();
                }
            }
        }
        return instance;
    }

    private WhitespaceTokenWriter() {
    }

    public @Mandatory String write(@Mandatory WhitespaceToken token) {
        String text = token.getText();
        StringBuilder builder = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                builder.append(" ");
            } else if (ch == '\s' || ch == '\t') {
                builder.append(ch);
            } else {
                throw new WriterException("Unsupported whitespace with code " + (int)ch + ".");
            }
        }
        return builder.toString();
    }

    public @Mandatory List<String> writeLines(@Mandatory WhitespaceToken token) {
        String text = token.getText();
        List<String> lines = new List<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                lines.addLast(builder.toString());
                builder = new StringBuilder();
            } else if (ch == '\s' || ch == '\t') {
                builder.append(ch);
            } else {
                throw new WriterException("Unsupported whitespace with code " + (int)ch + ".");
            }
        }
        lines.addLast(builder.toString());
        return lines;
    }
}
