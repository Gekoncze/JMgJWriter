package cz.mg.java.writer.services.formatting;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;

public class Indentation {
    private static final String INDENTATION = "    ";
    public static final int LENGTH = 4;

    private static volatile @Service Indentation instance;

    public static @Service Indentation getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Indentation();
                }
            }
        }
        return instance;
    }

    private Indentation() {
    }

    public @Mandatory List<String> add(@Mandatory List<String> lines) {
        List<String> result = new List<>();
        for (String line : lines) {
            if (line.isBlank()) {
                result.addLast("");
            } else {
                result.addLast(INDENTATION + line);
            }
        }
        return result;
    }
}
