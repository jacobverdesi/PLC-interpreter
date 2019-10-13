import org.junit.After;
import org.junit.Before;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JottTest {
    @Test
    public void zero() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.out;
        List<String> prog = FileReader.readFile("tests/prog0.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog0.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }
    @Test
    public void one() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.out;
        List<String> prog = FileReader.readFile("tests/prog1.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog1.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }
    @Test
    public void two() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.out;
        List<String> prog = FileReader.readFile("tests/prog2.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog2.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }
    @Test
    public void three() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.err;
        List<String> prog = FileReader.readFile("tests/prog3.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog3.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }
    @Test
    public void four() {
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        final PrintStream originalOut = System.err;
        List<String> prog = FileReader.readFile("tests/prog4.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog4.out");
        String ex = String.join("\r\n", expected);
        System.out.println("test");
        assertEquals(expected.get(0), errContent.toString());
        System.setErr(originalOut);
    }
    @Test
    public void five() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.out;
        List<String> prog = FileReader.readFile("tests/prog5.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog5.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }
    @Test
    public void six() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.out;
        List<String> prog = FileReader.readFile("tests/prog6.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog6.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }
    @Test
    public void seven() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.out;
        List<String> prog = FileReader.readFile("tests/prog7.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog7.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }
    @Test
    public void eight() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.out;
        List<String> prog = FileReader.readFile("tests/prog8.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog8.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }
    @Test
    public void nine() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.out;
        List<String> prog = FileReader.readFile("tests/prog9.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog9.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }
    @Test
    public void ten() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final PrintStream originalOut = System.out;
        List<String> prog = FileReader.readFile("tests/prog10.j");
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
        List<String> expected = FileReader.readFile("testouputs/prog10.out");
        String ex = String.join("\r\n", expected);
        assertEquals(ex+"\r\n", outContent.toString());
        System.setOut(originalOut);
    }



}