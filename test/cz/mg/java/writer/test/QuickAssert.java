package cz.mg.java.writer.test;

import cz.mg.annotations.classes.Static;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.test.Assertions;

public @Static class QuickAssert {
    public static void compare(@Mandatory List<String> expectations, @Mandatory List<String> reality) {
        Assertions.assertThatCollection(reality)
            .withMessage("Incorrect code generated.")
            .withFormatFunction(s -> '"' + s + '"')
            .isEqualTo(expectations);
    }
}
