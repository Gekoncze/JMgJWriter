package cz.mg.java.writer.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.writer.services.formatting.Indentation;

public @Component class LineMeasure {
    private static final int DELIMITER = 2;

    private int length;

    public LineMeasure() {
    }

    public @Mandatory LineMeasure addIndentation(int level) {
        length += level * Indentation.LENGTH;
        return this;
    }

    public @Mandatory LineMeasure addBrackets() {
        length += 2;
        return this;
    }

    public @Mandatory LineMeasure addPart(@Mandatory String part) {
        length += part.length();
        return this;
    }

    public @Mandatory LineMeasure addList(@Mandatory List<String> list) {
        for (String element : list) {
            length += element.length();
        }
        length += Math.max(0, DELIMITER * (list.count() - 1));
        return this;
    }

    public int length() {
        return length;
    }
}
