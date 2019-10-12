
import java.util.List;
import java.util.Map;

public class Jott {

    public static void main(String[] args){
        //List<String> prog = FileReader.readFile(args[0]);
        List<String> prog = FileReader.readFile("tests/prog0.j");

        List<String> table = FileReader.readFile("LALR(1) parse table");
        List<String> rules = FileReader.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);

        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
//        System.out.println(tokens);
//        Tokenizer.printTokens(tokens);
//        Tokenizer.printTerminals(tokens);
//        //Parser.printTable(matrix);
//        System.out.println();
        TreeNode tree=Parser.parseTree(rules,matrix,tokens);
        TreeInterpreter.runTree(tree);
    }
}
