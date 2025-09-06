package cz.mg.java.writer.services.tokens.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.java.writer.exceptions.WriterException;
import cz.mg.token.tokens.brackets.Brackets;
import cz.mg.token.tokens.brackets.CurlyBrackets;
import cz.mg.token.tokens.brackets.RoundBrackets;
import cz.mg.token.tokens.brackets.SquareBrackets;

public @Service class BracketsWriters {
    private static volatile @Service BracketsWriters instance;

    public static @Service BracketsWriters getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new BracketsWriters();
                    instance.roundBracketsWriter = RoundBracketsWriter.getInstance();
                    instance.squareBracketsWriter = SquareBracketsWriter.getInstance();
                    instance.curlyBracketsWriter = CurlyBracketsWriter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service RoundBracketsWriter roundBracketsWriter;
    private @Service SquareBracketsWriter squareBracketsWriter;
    private @Service CurlyBracketsWriter curlyBracketsWriter;

    private BracketsWriters() {
    }

    @SuppressWarnings("rawtypes")
    public @Mandatory BracketsWriter<Brackets> get(@Mandatory Brackets brackets) {
        return switch (brackets) {
            case RoundBrackets ignored -> (BracketsWriter) roundBracketsWriter;
            case SquareBrackets ignored -> (BracketsWriter) squareBracketsWriter;
            case CurlyBrackets ignored -> (BracketsWriter) curlyBracketsWriter;
            default -> throw new WriterException(
                "Unsupported brackets of type " + brackets.getClass().getSimpleName() + "."
            );
        };
    }
}
