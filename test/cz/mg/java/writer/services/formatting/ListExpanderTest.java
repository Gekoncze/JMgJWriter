package cz.mg.java.writer.services.formatting;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.java.writer.test.LineAssert;

public @Test class ListExpanderTest {
    private static final int SHORT = 20;
    private static final int LONG = 200;

    public static void main(String[] args) {
        System.out.print("Running " + ListExpanderTest.class.getSimpleName() + " ... ");

        ListExpanderTest test = new ListExpanderTest();
        test.testExpandEmptyShort();
        test.testExpandEmptyLong();
        test.testExpandSingleShort();
        test.testExpandSingleLong();
        test.testExpandMultipleShort();
        test.testExpandMultipleLong();

        System.out.println("OK");
    }

    private final @Service ListExpander expander = ListExpander.getInstance();

    private void testExpandEmptyShort() {
        LineAssert.assertEquals(
            new List<>("()"),
            expander.expand(new List<>(), SHORT)
        );
    }

    private void testExpandEmptyLong() {
        LineAssert.assertEquals(
            new List<>("()"),
            expander.expand(new List<>(), LONG)
        );
    }

    private void testExpandSingleShort() {
        LineAssert.assertEquals(
            new List<>("(foobar)"),
            expander.expand(new List<>("foobar"), SHORT)
        );
    }

    private void testExpandSingleLong() {
        LineAssert.assertEquals(
            new List<>(
                "(",
                "    foobar",
                ")"
            ),
            expander.expand(new List<>("foobar"), LONG)
        );
    }

    private void testExpandMultipleShort() {
        LineAssert.assertEquals(
            new List<>("(foo, bar, foobar)"),
            expander.expand(new List<>("foo", "bar", "foobar"), SHORT)
        );
    }

    private void testExpandMultipleLong() {
        LineAssert.assertEquals(
            new List<>(
                "(",
                "    foo,",
                "    bar,",
                "    foobar",
                ")"
            ),
            expander.expand(new List<>("foo", "bar", "foobar"), LONG)
        );
    }
}
