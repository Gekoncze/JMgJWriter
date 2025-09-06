package cz.mg.java.writer.services.tokens.brackets;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.writer.services.tokens.TokenWriter;
import cz.mg.token.tokens.brackets.Brackets;

public @Service interface BracketsWriter<B extends Brackets> extends TokenWriter<B> {
    @Override
    @Mandatory String write(@Mandatory B brackets);

    @Override
    @Mandatory List<String> writeLines(@Mandatory B brackets);
}
