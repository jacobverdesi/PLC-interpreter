import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Jott {
    public static void testProgs(List<Integer> progs, List<String> table, List<String> rules) {
        for(int i: progs){
            System.out.println("\nprog"+i+".j tree:");
            System.out.println("================");
            jott("tests/prog"+i+".j",table,rules,false);
        }
    }
    public static int jott(String progFile,List<String> table,List<String> rules,Boolean fullPrint) {
        List<String> prog = FileReader.readFile(progFile);
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens = null;
        TreeNode tree = null;
        try {
            assert prog != null;
            tokens = Tokenizer.dfaTokenizer(prog);
        }
        catch (SyntaxError se){
            System.err.printf("Syntax Error: %s , \"%s\" (%s:%d)",se.getMessage(),prog.get(se.getLine()),progFile,se.getLine()+1);
            return -1;
        }
        if(fullPrint) {
            System.out.println("\nTokens:");
            Tokenizer.printTerminals(tokens);
        }
        try {
            tree = Parser.parseTree(rules, matrix, tokens);
        }
        catch (SyntaxError se){
            System.out.println(se.getLine());
            System.err.printf("Syntax Error: %s , \"%s\" (%s:%d)",se.getMessage(),prog.get(se.getLine()),progFile,se.getLine()+1);
            return -1;
        }
        if(fullPrint) {
            System.out.println("\nProgram Tree:");
            TreeNode.printTree(tree);
            System.out.println("\nOutput:");
        }
        try {
            TreeInterpreter.runTree(tree);
        }
        catch (RuntimeError re){
            System.err.printf("Runtime Error: %s , \"%s\" (%s:%d)",re.getMessage(),prog.get(re.getLine()),progFile,re.getLine()+1);
            return -1;
        }
        return 1;
    }

    public static void main(String[] args)  {
        String progFile="tests/prog3.j";
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        //jott(progFile,table,rules,false);

        //ArrayList l= new ArrayList(Arrays.asList(0,1,2,5,6,7,8,9,11,12,13,14,15,17,23,24));
        ArrayList l= new ArrayList(Arrays.asList(3,4,10,16,17,20,21,22));

        testProgs(l,table,rules);
    }
}
