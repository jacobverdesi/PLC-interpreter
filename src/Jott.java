
import com.sun.source.tree.Tree;

import java.util.List;
import java.util.Map;

public class Jott {
    public static void testProgs(List<Integer> progs, List<String> rules, List<List<String>> matrix){
        for(int i: progs){
            List<String> prog = FileReader.readFile("tests/prog"+i+".j");
            System.out.println("\nprog"+i+".j tree:");
            System.out.println("================");
            List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
            System.out.println("Tokenized");
            TreeNode tree=Parser.parseTree(rules,matrix,tokens);
            TreeNode.printTree(tree);

        }
    }

    public static void main(String[] args){
        List<String> prog = FileReader.readFile(args[0]);
        String s="";
        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("Grammar2");
        List<List<String>> matrix = Parser.parseTable(table);

        System.out.println("\nTokens:");
        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        Tokenizer.printTerminals(tokens);

        System.out.println("\nProgram Tree:");
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeNode.reverseTree(tree);
        TreeNode.printTree(tree);

        System.out.println("\nOutput:");
        TreeInterpreter.runTree(tree);
    }
}
