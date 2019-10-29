
import java.util.List;
import java.util.Map;

public class Jott {

    public static void main(String[] args){
        List<String> prog = FileReader.readFile(args[0]);

        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);

        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);

        System.out.println(tokens);
//        Tokenizer.printTokens(tokens);
        Tokenizer.printTerminals(tokens);
//       //Parser.printTable(matrix);
//
       // List<String> prog = FileReader.readFile("tests/prog8.j");
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        System.out.println("\nProgram Tree: \n");
        TreeNode.printTree(tree);
        TreeInterpreter.runTree(tree);
    }
}
