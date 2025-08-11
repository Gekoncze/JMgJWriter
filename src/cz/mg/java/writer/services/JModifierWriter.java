package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.Collection;
import cz.mg.collections.components.StringJoiner;
import cz.mg.java.entities.JModifier;

public @Service class JModifierWriter {
    private static volatile @Service JModifierWriter instance;

    public static @Service JModifierWriter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new JModifierWriter();
                }
            }
        }
        return instance;
    }

    private JModifierWriter() {
    }

    public @Mandatory String write(@Mandatory JModifier modifier) {
        return modifier.name().toLowerCase();
    }

    public @Mandatory String write(@Mandatory Collection<JModifier> modifiers) {
        return new StringJoiner<>(modifiers)
            .withConverter(this::write)
            .withDelimiter(" ")
            .join();
    }
}
