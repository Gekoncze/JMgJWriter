package cz.mg.java.writer.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;

public @Component class Escape {
    private @Mandatory String text;

    public Escape(@Mandatory String text) {
        this.text = text;
    }

    public @Mandatory Escape backspace() {
        text = text.replace("\b", "\\b");
        return this;
    }

    public @Mandatory Escape backslash() {
        text = text.replace("\\", "\\\\");
        return this;
    }

    public @Mandatory Escape carriageReturn() {
        text = text.replace("\r", "\\r");
        return this;
    }

    public @Mandatory Escape doubleQuote() {
        text = text.replace("\"", "\\\"");
        return this;
    }

    public @Mandatory Escape newline() {
        text = text.replace("\n", "\\n");
        return this;
    }

    public @Mandatory Escape singleQuote() {
        text = text.replace("'", "\\'");
        return this;
    }

    public @Mandatory Escape tab() {
        text = text.replace("\t", "\\t");
        return this;
    }

    public @Mandatory String get() {
        return text;
    }
}
