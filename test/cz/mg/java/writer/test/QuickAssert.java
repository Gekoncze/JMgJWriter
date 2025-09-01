package cz.mg.java.writer.test;

import cz.mg.annotations.classes.Static;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

public @Static class QuickAssert {
    public static void compare(@Mandatory List<String> expectations, @Mandatory List<String> reality) {
        Assert.assertThatCollections(expectations, reality)
            .withMessage("Incorrect code generated.")
            .withPrintFunction(s -> '"' + s + '"')
            .areEqual();
    }
}
