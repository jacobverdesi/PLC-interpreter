
import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Jott {
    public static void testProgs(List<Integer> progs, List<String> table, List<String> rules){
        List<List<String>> matrix = Parser.parseTable(table);
        for(int i: progs){
            List<String> prog = FileReader.readFile("tests/prog"+i+".j");
            System.out.println("\nprog"+i+".j tree:");
            System.out.println("================");
            List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
            //System.out.println("Tokenized");
            TreeNode tree=Parser.parseTree(rules,matrix,tokens);
            //TreeNode.printTree(tree);
            TreeInterpreter.runTree(tree);
        }
    }
    public static void jott(String progFile,List<String> table,List<String> rules){
        List<String> prog = FileReader.readFile(progFile);
        List<List<String>> matrix = Parser.parseTable(table);

        System.out.println("\nTokens:");
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        Tokenizer.printTerminals(tokens);

        System.out.println("\nProgram Tree:");
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeNode.printTree(tree);

        System.out.println("\nOutput:");
        TreeInterpreter.runTree(tree);
    }

    public static void main(String[] args){

        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        //jott("tests/prog9.j",table,rules);
        ArrayList l= new ArrayList(Arrays.asList(0,1,2,5,6,7,8,9,11,12,13,14,15,17,23,24));
        testProgs(l,table,rules);
    }
}
