
import java.io.*;
import java.util.*;

import java.util.logging.Logger;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isLetter;

public class Scan {
    static public final String DELIMITER = "((?<=%1$s)|(?=%1$s))";
   // static public final String DELIMITERS = String.format(DELIMITER+""+DELIMITER+DELIMITER+DELIMITER,";","\\(","\\)",",");
    private static List<String> readFile(String filename){
        List<String> lines = new ArrayList<String>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
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
    private static List<String> tokenizer(List<String> lines){
        List<String> tokens = new ArrayList<String>();
        int lineNum=0;
        for (String line:lines){
            String[] tok=line.split(String.format(DELIMITER,"[()+\\-\\*/=,;]"));
            for (String to:tok) {
                String[] tok2=to.split("\\s");
                tokens.addAll(Arrays.asList(tok2));
            }
        }
        tokens.removeIf(String::isEmpty);
        return tokens;
    }

    public static void printList(List<String> tokens){
        for (String token:tokens) {
            if (token.equals(";")) {
                System.out.println(""+token+"");
            } else {
                System.out.print("["+token+"] ");
            }
        }
    }

    public static void main(String[] args){
        List<String> a = readFile("src/program.j");
        List<String> tokens=tokenizer(a);
        printList(tokens);

    }
}
