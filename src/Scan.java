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

    private static List<List> dfaTokenizer(List<String> lines){
        List<List> tokens= new ArrayList<>();
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
                            if(!Character.isLetter(next) && !Character.isDigit(next)) state.reset(Tokens.ID);
                        }
                        else if (curr == '.') state.setState(2);
                        else if (Character.isDigit(curr)){
                            state.setState(3);
                            if(next!='.' && !Character.isDigit(next)) state.reset(Tokens.INT);
                        }
                        else if (curr == '=') state.reset(Tokens.ASSIGN);
                        else if (curr == '(') state.reset(Tokens.START_PAREN);
                        else if (curr == ')') state.reset(Tokens.END_PAREN);
                        else if (curr == ';') state.reset(Tokens.END_STMT);
                        else if (curr == '+') state.reset(Tokens.PLUS);
                        else if (curr == '-') state.reset(Tokens.MINUS);
                        else if (curr == '*') state.reset(Tokens.MULTI);
                        else if (curr == '/') state.reset(Tokens.DIVIDE);
                        else if (curr == ',') state.reset(Tokens.COMMA);
                        else if (curr == '^') state.reset(Tokens.POWER);
                        else if (curr == '\"') state.setState(14);
                        else error(lineNum,i,curr);

                        break;
                    case 1:
                        if (Character.isLetter(curr) || Character.isDigit(curr)){
                            if(!Character.isLetter(next) && !Character.isDigit(next)){
                                if (state.getToken().equals("print")) state.reset(Tokens.PRINT);
                                else if (state.getToken().equals("concat")) state.reset(Tokens.CONCAT);
                                else if (state.getToken().equals("charAt")) state.reset(Tokens.CHARAT);
                                else state.reset(Tokens.ID);
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
                            if(next!='.' && !Character.isDigit(next)) state.reset(Tokens.INT);
                            continue;
                        }
                        else if (curr=='.'){
                            state.setState(4);
                            if(!Character.isDigit(next))state.reset(Tokens.DOUBLE);
                        }
                        else error(lineNum,i,curr);
                        break;
                    case 4:
                        if(Character.isDigit(curr)){
                            if(!Character.isDigit(next))state.reset(Tokens.DOUBLE);
                            continue;
                        }
                        else error(lineNum,i,curr);
                        break;
                    case 14:
                        if(Character.isDigit(curr) || Character.isLetter(curr) || curr== ' ') continue;
                        else if (curr=='\"') state.reset(Tokens.STRING);
                        else error(lineNum,i,curr);
                        break;
                }
            }
            lineNum++;
            List<Map.Entry<String,Tokens>> a= new ArrayList<>(state.getTokens());
            tokens.add(a);
            state.resetTokens();
        }
        return tokens;
    }
    private static List<List> getPrimitives (List<List> tokens){
        List<List> prim=new ArrayList<>(new ArrayList<>());
        for (List line: tokens){
            List<String> primLine=new ArrayList<>();
            for (Object token: line){
                if(token.equals("print")) primLine.add("<print>");
                else if (token.equals("(")) primLine.add("<left_paren>");
                else if (token.equals(")")) primLine.add("<right_paren>");
                else if ((token.toString().contains("."))) primLine.add("<dbl>");

            }
            prim.add(primLine);
        }
        return prim;
    }

    /**
     * A pretty print to see the tokens
     * @param tokens
     */
    private static void printList(List<List> tokens){
        for (List line: tokens) {
            for (Object token : line) {
                    System.out.print("[" + token + "] ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        List<String> a = readFile("src/program.j");
        List<List> tokens=dfaTokenizer(a);
        //System.out.println(tokens);
        printList(tokens);



    }
}
