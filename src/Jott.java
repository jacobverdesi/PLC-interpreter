
import com.sun.source.tree.Tree;

import java.util.List;
import java.util.Map;

public class Jott {

    public static void main(String[] args){
        List<String> prog = FileReader.readFile(args[0]);

        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);

        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);

        //System.out.println(tokens);
//        Tokenizer.printTokens(tokens);
        Tokenizer.printTerminals(tokens);
//       //Parser.printTable(matrix);
//
       // List<String> prog = FileReader.readFile("tests/prog8.j");
        System.out.println("\nProgram Tree: \n");
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeNode.printTree(tree);

        //TreeNode.printTree(tree);
        System.out.println("\nOutput: \n");

        TreeInterpreter.runTree(tree);
    }
}
