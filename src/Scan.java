/**
 * Interpreter project
 * Scanner class?
 * @author: Jacob Verdesi (jxv3386@rit.edu()
 */

import java.io.*;
import java.util.*;

import java.util.logging.Logger;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isLetter;

public class Scan {
    static private final String DELIMITER = "((?<=%1$s)|(?=%1$s))";

    /**
     * Takes in file name reads line by line
     * @param filename
     * @return list of lines
     */
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

    /**
     * Tokenizer goes through each line and splits at the Delimiters
     * Then it splits at the strings
     * Remove empty tokens
     * //TODO: concatnate quote tokens together
     * @param lines
     * @return
     */
    private static List<String> tokenizer(List<String> lines){
        List<String> tokens = new ArrayList<String>();
        int lineNum=0;
        for (String line:lines){
            String[] tok=line.split(String.format(DELIMITER,"[()+\\-\\*/^=,;]"));
            for (String to:tok) {
                String[] tok2=to.split("\\s");
                tokens.addAll(Arrays.asList(tok2));
            }
        }
        tokens.removeIf(String::isEmpty);
        return tokens;
    }

    /**
     * A pretty print to see the tokens
     * @param tokens
     */
    private static void printList(List<String> tokens){
        for (String token:tokens) {
            if (token.equals(";")) {
                System.out.println("["+token+"]");
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
