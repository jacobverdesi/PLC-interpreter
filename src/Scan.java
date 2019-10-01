/**
 * Interpreter project
 * Scanner class?
 * @author: Jacob Verdesi jxv3386@rit.edu()
 */

import java.io.*;
import java.util.*;

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
    private static void error(int line , int column,char curr){
        System.err.format("Invalid syntax line: %s column: %s character: %s\n", line, column,curr);
        System.exit(0);
    }

    private static List<List<Map.Entry<String, TERMINAL>>> dfaTokenizer(List<String> lines){
        List<List<Map.Entry<String, TERMINAL>>> tokens= new ArrayList<>();
        int lineNum=0;
        DFAstate state=new DFAstate();
        for(String line: lines){
            char[] chars=line.toCharArray();
            for (int i=0; i < chars.length;i++){
                if(chars[i]=='/' && chars[i+1]=='/') break; // if find comment end line
                if(state.getState()==0 && (chars[i]==' '|| chars[i]=='\n' || chars[i]=='\t')) continue;

                state.setCurr(chars[i]);
                if(i<chars.length-1) state.setNext(chars[i + 1]);
                char curr=state.getCurr();
                char next=state.getNext();
                //System.out.println("i: "+i+" current: "+state.getCurr()+" next: "+state.getNext()+ " state: "+state.getState());
                switch (state.getState()){
                    case 0:
                        if (Character.isLetter(curr)){
                            state.setState(1);
                            if(!Character.isLetter(next) && !Character.isDigit(next)) state.reset(TERMINAL.ID);
                        }
                        else if (curr == '.') state.setState(2);
                        else if (Character.isDigit(curr)){
                            state.setState(3);
                            if(next!='.' && !Character.isDigit(next)) state.reset(TERMINAL.INT);
                        }
                        else if (curr == '=') state.reset(TERMINAL.ASSIGN);
                        else if (curr == '(') state.reset(TERMINAL.L_PAREN);
                        else if (curr == ')') state.reset(TERMINAL.R_PAREN);
                        else if (curr == ';') state.reset(TERMINAL.END);
                        else if (curr == '+') state.reset(TERMINAL.PLUS);
                        else if (curr == '-') state.reset(TERMINAL.MINUS);
                        else if (curr == '*') state.reset(TERMINAL.MULTI);
                        else if (curr == '/') state.reset(TERMINAL.DIVIDE);
                        else if (curr == ',') state.reset(TERMINAL.COMMA);
                        else if (curr == '^') state.reset(TERMINAL.POWER);
                        else if (curr == '\"') state.setState(14);
                        else error(lineNum,i,curr);

                        break;
                    case 1:
                        if (Character.isLetter(curr) || Character.isDigit(curr)){
                            if(!Character.isLetter(next) && !Character.isDigit(next)){
                                if (state.getToken().equals("print")) state.reset(TERMINAL.PRINT);
                                else if (state.getToken().equals("concat")) state.reset(TERMINAL.CONCAT);
                                else if (state.getToken().equals("charAt")) state.reset(TERMINAL.CHARAT);
                                else if (state.getToken().equals("Integer")) state.reset(TERMINAL.I_ASMT);
                                else if (state.getToken().equals("Double")) state.reset(TERMINAL.D_ASMT);
                                else if (state.getToken().equals("String")) state.reset(TERMINAL.S_ASMT);
                                else state.reset(TERMINAL.ID);
                            }
                            continue;
                        }
                        else error(lineNum,i,curr);

                    case 2:
                        if(Character.isDigit(curr)) state.setState(4);
                        else error(lineNum,i,curr);
                        break;
                    case 3:
                        if(Character.isDigit(curr)){
                            if(next!='.' && !Character.isDigit(next)) state.reset(TERMINAL.INT);
                            continue;
                        }
                        else if (curr=='.'){
                            state.setState(4);
                            if(!Character.isDigit(next))state.reset(TERMINAL.DOUBLE);
                        }
                        else error(lineNum,i,curr);
                        break;
                    case 4:
                        if(Character.isDigit(curr)){
                            if(!Character.isDigit(next))state.reset(TERMINAL.DOUBLE);
                            continue;
                        }
                        else error(lineNum,i,curr);
                        break;
                    case 14:
                        if(Character.isDigit(curr) || Character.isLetter(curr) || curr== ' ') continue;
                        else if (curr=='\"') state.reset(TERMINAL.STRING);
                        else error(lineNum,i,curr);
                        break;
                }
            }
            lineNum++;
            List<Map.Entry<String, TERMINAL>> a= new ArrayList<>(state.getTokens());
            tokens.add(a);
            state.resetTokens();
        }
        return tokens;
    }
    /**
     * A pretty print to see the tokens
     * @param tokens
     */

    private static void printList(List<List<Map.Entry<String, TERMINAL>>> tokens){
        System.out.println("Tokens\n");

        for (List<Map.Entry<String, TERMINAL>> line: tokens) {
            for (Map.Entry<String, TERMINAL> token: line) {
                    System.out.print("[" + token.getKey() + "] ");
            }
            System.out.println();
        }
        System.out.println("Terminals\n");
        for (List<Map.Entry<String, TERMINAL>> line: tokens) {
            for (Map.Entry<String, TERMINAL> token: line) {
                System.out.print("" + token.getValue() + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        List<String> a = readFile("src/program.j");
        List<List<Map.Entry<String, TERMINAL>>> tokens=dfaTokenizer(a);
        //System.out.println(tokens);
        printList(tokens);
    }
}
