package cz.mg.java.writer.services.formatting;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;

public class ListExpander {
    private static final int LIMIT = 110;

    private static volatile @Service ListExpander instance;

    public static @Service ListExpander getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new ListExpander();
                }
            }
        }
        return instance;
    }

    private ListExpander() {
    }

    public @Mandatory List<String> expand(@Mandatory List<String> list, int estimatedLineLength) {
        if (list.isEmpty() || estimatedLineLength < LIMIT) {
            return writeListOnSingleLine(list);
        } else {
            return writeListOnSeparateLines(list);
        }
    }

    private @Mandatory List<String> writeListOnSingleLine(@Mandatory List<String> list) {
        String joinedInput = new StringJoiner<>(list)
            .withDelimiter(", ")
            .join();

        return new List<>("(" + joinedInput + ")");
    }

    private @Mandatory List<String> writeListOnSeparateLines(@Mandatory List<String> list) {
        List<String> lines = new List<>("(");
        int i = 0;
        for (String element : list) {
            String delimiter = i < list.count() - 1 ? "," : "";
            lines.addLast("    " + element + delimiter);
            i++;
        }
        lines.addLast(")");
        return lines;
    }
}
