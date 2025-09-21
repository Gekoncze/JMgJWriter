package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;

public @Service class LineSeparator {
    private static volatile @Service LineSeparator instance;

    public static @Service LineSeparator getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new LineSeparator();
                }
            }
        }
        return instance;
    }

    private LineSeparator() {
    }

    public @Mandatory List<String> split(@Mandatory String text) {
        List<String> lines = new List<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                lines.addLast(builder.toString());
                builder = new StringBuilder();
            } else {
                builder.append(ch);
            }
        }
        lines.addLast(builder.toString());
        return lines;
    }
}
