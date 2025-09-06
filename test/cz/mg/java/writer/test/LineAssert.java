package cz.mg.java.writer.test;

import cz.mg.annotations.classes.Static;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.test.Assertions;

public @Static class LineAssert {
    public static void assertEquals(@Mandatory List<String> expectations, @Mandatory List<String> reality) {
        Assertions.assertThatCollection(reality)
            .withMessage("Incorrect code generated.")
            .withFormatFunction(s -> '"' + s + '"')
            .isEqualTo(expectations);
    }
}
