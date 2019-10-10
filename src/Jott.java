import java.util.List;
import java.util.Map;

public class Jott {

    public static void main(String[] args){
        List<String> prog = readFile.readFile(args[0]);
        List<String> table = readFile.readFile("LALR(1) parse table");
        List<String> rules = readFile.readFile("GRAMMAR");
        List<List<String>> matrix = Parser.parseTable(table);

        List<List<Map.Entry<String, TERMINAL>>> tokens=Tokenizer.dfaTokenizer(prog);
        System.out.println(tokens);
        Tokenizer.printTokens(tokens);
        Tokenizer.printTerminals(tokens);
        Parser.printTable(matrix);
        System.out.println();
        System.out.println();
        System.out.println(Parser.parseTree(rules,matrix,tokens));
    }
}