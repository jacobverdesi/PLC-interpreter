import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Jott {
    /**
     * Takes in file name reads line by line
     * @param filename
     * @return list of lines
     */
    public static List<String> readFile(String filename){
        List<String> lines = new ArrayList<String>();
        try{
            BufferedReader reader = new BufferedReader(new java.io.FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
            reader.close();
            return lines;
        }
        catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.\n", filename);
            e.printStackTrace();
            return null;
        }
    }
    public static void testProgs(List<Integer> progs, List<String> table, List<String> rules) {
        for(int i: progs){
            System.out.println("\nprog"+i+".j tree:");
            System.out.println("================");
            jott("tests/prog"+i+".j",table,rules,false);

        }
    }
    public static int jott(String progFile,List<String> table,List<String> rules,Boolean fullPrint) {
        List<String> prog = readFile(progFile);
        List<List<String>> matrix = Parser.parseTable(table);
        List<List<Map.Entry<String, TERMINAL>>> tokens;
        TreeNode tree;
        try {
            assert prog != null;
            tokens = Tokenizer.dfaTokenizer(prog);
        }
        catch (SyntaxError se){
            System.err.printf("Syntax Error: %s , \"%s\" (%s:%d)",se.getMessage(),prog.get(se.getLine()-1),progFile,se.getLine());
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
            System.err.printf("Syntax Error: %s , \"%s\" (%s:%d)",se.getMessage(),prog.get(se.getLine()-1),progFile,se.getLine());
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
            System.err.printf("Runtime Error: %s , \"%s\" (%s:%d)",re.getMessage(),prog.get(re.getLine()-1),progFile,re.getLine());

            return -1;
        }

        return 1;
    }

    public static void main(String[] args)  {
        String progFile=args[0];
        List<String> table = readFile("LALR(1) parse table");
        List<String> rules = readFile("GRAMMAR");
        jott(progFile,table,rules,false);
        //ArrayList l= new ArrayList(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,20,21,22,23,24));
        //ArrayList l= new ArrayList(Arrays.asList(0,1,2,5,6,7,8,9,11,12,13,14,15,17,23,24));
        //ArrayList l= new ArrayList(Arrays.asList(3,4,10,16,17,21,22));
        //testProgs(l,table,rules);
    }
}
