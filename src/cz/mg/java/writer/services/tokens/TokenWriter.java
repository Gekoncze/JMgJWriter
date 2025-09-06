package cz.mg.java.writer.services.tokens;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.token.Token;

public @Service interface TokenWriter<T extends Token> {
    @Mandatory String write(@Mandatory T token);

    default @Mandatory List<String> writeLines(@Mandatory T token) {
        return new List<>(write(token));
    }
}
