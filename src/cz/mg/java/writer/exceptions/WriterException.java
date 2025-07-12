package cz.mg.java.writer.exceptions;

import cz.mg.annotations.classes.Error;
import cz.mg.annotations.requirement.Mandatory;

public @Error class WriterException extends RuntimeException {
    public WriterException(@Mandatory String message) {
        super(message);
    }

    public WriterException(@Mandatory String message, @Mandatory Throwable cause) {
        super(message, cause);
    }
}
