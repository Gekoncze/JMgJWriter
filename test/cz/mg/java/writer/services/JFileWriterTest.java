package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.file.page.Page;
import cz.mg.java.entities.*;
import cz.mg.java.writer.test.QuickAssert;
import cz.mg.test.Assertions;
import cz.mg.token.test.TokenFactory;

import java.nio.file.Path;

public @Test class JFileWriterTest {
    private static final Path PATH = Path.of("my/test/directory");

    public static void main(String[] args) {
        System.out.print("Running " + JFileWriterTest.class.getSimpleName() + " ... ");

        JFileWriterTest test = new JFileWriterTest();
        test.testWriteStructure();
        test.testWriteComment();
        test.testWritePackageLine();
        test.testWriteSingleImport();
        test.testWriteMultipleImports();
        test.testWriteComplex();

        System.out.println("OK");
    }

    private final @Service JFileWriter writer = JFileWriter.getInstance();
    private final @Service TokenFactory t = TokenFactory.getInstance();

    private void testWriteStructure() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        JFile file = new JFile();
        file.setPath(PATH);
        file.setStructure(jClass);

        compare(
            PATH,
            new List<>(
                "class FooBar {",
                "}"
            ),
            writer.write(file)
        );
    }

    private void testWriteComment() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        JFile file = new JFile();
        file.setComment("Test file comment.\nFor example for copyright notices.");
        file.setPath(PATH);
        file.setStructure(jClass);

        compare(
            PATH,
            new List<>(
                "/**",
                " * Test file comment.",
                " * For example for copyright notices.",
                " */",
                "",
                "class FooBar {",
                "}"
            ),
            writer.write(file)
        );
    }

    private void testWritePackageLine() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        JFile file = new JFile();
        file.setPath(PATH);
        file.setStructure(jClass);

        JPackageLine packageLine = new JPackageLine(new List<>("cz", "mg", "java", "writer"), null);
        file.setPackageLine(packageLine);

        compare(
            PATH,
            new List<>(
                "package cz.mg.java.writer;",
                "",
                "class FooBar {",
                "}"
            ),
            writer.write(file)
        );
    }

    private void testWriteSingleImport() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        JFile file = new JFile();
        file.setPath(PATH);
        file.setStructure(jClass);

        file.getImports().addLast(new JImport(new List<>("cz", "mg", "java", "writer", "JWriter"), null));

        compare(
            PATH,
            new List<>(
                "import cz.mg.java.writer.JWriter;",
                "",
                "class FooBar {",
                "}"
            ),
            writer.write(file)
        );
    }

    private void testWriteMultipleImports() {
        JClass jClass = new JClass();
        jClass.setName("FooBar");

        JFile file = new JFile();
        file.setPath(PATH);
        file.setStructure(jClass);

        file.setImports(new List<>(
            new JImport(new List<>("cz", "mg", "java", "writer", "JWriter"), null),
            new JImport(new List<>("cz", "mg", "java", "writer", "JWriterTest"), null)
        ));

        compare(
            PATH,
            new List<>(
                "import cz.mg.java.writer.JWriter;",
                "import cz.mg.java.writer.JWriterTest;",
                "",
                "class FooBar {",
                "}"
            ),
            writer.write(file)
        );
    }

    private void testWriteComplex() {
        JClass jClass = new JClass();
        jClass.setModifiers(new List<>(JModifier.PUBLIC));
        jClass.setName("Test");

        JVariable field = new JVariable();
        field.setModifiers(new List<>(JModifier.PRIVATE));
        field.setType(new JType("int"));
        field.setName("test");
        jClass.getFields().addLast(field);

        JMethod method = new JMethod();
        method.setModifiers(new List<>(JModifier.PUBLIC));
        method.setOutput(new JType("int"));
        method.setName("getTest");
        method.setImplementation(new List<>(
            t.word("return"),
            t.whitespace(" "),
            t.word("test"),
            t.symbol(";")
        ));
        jClass.getMethods().addLast(method);

        JFile file = new JFile();
        file.setComment("Example copyright notice.");
        file.setPath(PATH);
        file.setStructure(jClass);

        JPackageLine packageLine = new JPackageLine(new List<>("cz", "mg", "java", "test"), "package comment");
        file.setPackageLine(packageLine);

        JImport jImport = new JImport(new List<>("cz", "mg", "collections", "*"), "import comment");
        file.getImports().addLast(jImport);

        compare(
            PATH,
            new List<>(
                "/**",
                " * Example copyright notice.",
                " */",
                "",
                "package cz.mg.java.test; // package comment",
                "",
                "import cz.mg.collections.*; // import comment",
                "",
                "public class Test {",
                "    private int test;",
                "",
                "    public int getTest() {",
                "        return test;",
                "    }",
                "}"
            ),
            writer.write(file)
        );
    }

    private void compare(
        @Mandatory Path expectedPath,
        @Mandatory List<String> expectedLines,
        @Mandatory Page reality
    ) {
        Assertions.assertThat(reality.getPath())
                .withMessage("Incorrect file path.")
                .isEqualTo(expectedPath);

        QuickAssert.compare(expectedLines, reality.getLines());
    }
}
