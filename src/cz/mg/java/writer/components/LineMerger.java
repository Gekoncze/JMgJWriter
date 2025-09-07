package cz.mg.java.writer.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadableList;
import cz.mg.collections.list.ReadableListItem;

public @Component class LineMerger {
    private @Mandatory String delimiter = "";
    private @Mandatory List<String> lines = new List<>();

    public LineMerger() {
    }

    public @Mandatory LineMerger delimiter(@Mandatory String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public @Mandatory LineMerger merge(@Mandatory String line) {
        return merge(new List<>(line));
    }

    public @Mandatory LineMerger merge(@Mandatory ReadableList<String> lines) {
        this.lines = merge(this.lines, delimiter, lines);
        return this;
    }

    public @Mandatory List<String> get() {
        List<String> result = lines;
        lines = new List<>();
        return result;
    }

    public static @Mandatory List<String> merge(
        @Mandatory ReadableList<String> a,
        @Mandatory String delimiter,
        @Mandatory ReadableList<String> b
    ) {
        if (a.isEmpty()) {
            return new List<>(b);
        }

        if (b.isEmpty()) {
            return new List<>(a);
        }

        List<String> lines = new List<>();

        for (ReadableListItem<String> item = a.getFirstItem(); item != null; item = item.getNextItem()) {
            if (item != a.getLastItem()) {
                lines.addLast(item.get());
            }
        }

        String lastLine = a.getLast();
        String firstLine = b.getFirst();
        lines.addLast(lastLine + delimiter + firstLine);

        for (ReadableListItem<String> item = b.getFirstItem(); item != null; item = item.getNextItem()) {
            if (item != b.getFirstItem()) {
                lines.addLast(item.get());
            }
        }

        return lines;
    }
}
