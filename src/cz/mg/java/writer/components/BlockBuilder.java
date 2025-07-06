package cz.mg.java.writer.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Component class BlockBuilder {
    private final List<List<String>> blocks = new List<>();

    public BlockBuilder() {
    }

    public @Mandatory BlockBuilder addLine(@Optional String line) {
        if (line != null) {
            blocks.addLast(new List<>(line));
        }
        return this;
    }

    public @Mandatory BlockBuilder addLines(@Optional List<String> lines) {
        if (lines != null && !lines.isEmpty()) {
            blocks.addLast(lines);
        }
        return this;
    }

    public @Mandatory List<String> build() {
        List<String> lines = new List<>();
        for (
            ListItem<List<String>> blockItem = blocks.getFirstItem();
            blockItem != null;
            blockItem = blockItem.getNextItem()
        ) {
            List<String> block = blockItem.get();
            lines.addCollectionLast(block);
            if (blockItem.getNextItem() != null) {
                lines.addLast("");
            }
        }
        return lines;
    }
}
