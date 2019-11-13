/**
 * Interpreter project
 * Scanner class?
 * @author: Jacob Verdesi jxv3386@rit.edu()
 */

import java.io.*;
import java.util.*;

public class Tokenizer {


    /**
     * Function that reads list of lines and tokenizes line using a DFA
     * tokens is a 2d list representing the lines and all the tokens in the line
     * this tokens list has a map inside which stores a String being the token and a TERMINAL enum which is the type
     * DFAstate is a class that is used to represent the current state of the dfa and other data
     * Iterate through list of lines then through each character of the line
     * The start state is 0
     * DFA process:
     * read new character and the next one update dfa
     * read current state goto that case
     * each case looks for specific current and next characters
     * if current is equivilent set new state ,
     * then checks if next is expected if not reset dfa and add the token to line of tokens
     * when reseting determine type of token and to value of the token key
     * if current is not found error out
     * @param lines
     * @return
     */
    public static List<List<Map.Entry<String, TERMINAL>>> dfaTokenizer(List<String> lines) throws SyntaxError{
        List<List<Map.Entry<String, TERMINAL>>> tokens= new ArrayList<>();
        int lineNum=0;
        DFAstate state=new DFAstate();
        for(String line: lines){
            char[] chars=line.toCharArray();
            for (int i=0; i < chars.length;i++){
                if(chars[i]=='/' &&chars.length>1&& chars[i+1]=='/') break; // skip rest of line if  finds a comment
                if(state.getState()==0 && (chars[i]==' '|| chars[i]=='\n' || chars[i]=='\t')) continue; //ignore these chars
                state.setCurr(chars[i]); // set the dfa current character
                if(i<chars.length-1) state.setNext(chars[i + 1]); // set the dfa's next character if in range
                else state.setNext(' '); //TODO: MIGHT HAVE BROKE SOMETHING
                char curr=state.getCurr(); // get curr character ....yes redundant
                char next=state.getNext(); // get next character ....yes redundant
                //System.out.println("i: "+i+" current: "+state.getCurr()+" next: "+state.getNext()+ " state: "+state.getState());
                //System.out.println(i+", "+state.getToken()+" ,"+curr+","+next+","+state.getState());

                switch (state.getState()){ // get the state and do something with it
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
                        else if (curr == '<') {
                            state.setState(16);
                            if(next!='=') state.reset(TERMINAL.LESS);
                        }
                        else if (curr == '>') {
                            state.setState(17);
                            if(next!='=') state.reset(TERMINAL.GREATER);
                        }
                        else if (curr == '!') {
                            state.setState(18);
                        }
                        else if (curr == '=') {
                            state.setState(5);
                            if(next!='=') state.reset(TERMINAL.ASSIGN);
                        }
                        else if (curr == '(') state.reset(TERMINAL.L_PAREN);
                        else if (curr == ')') state.reset(TERMINAL.R_PAREN);
                        else if (curr == ';') state.reset(TERMINAL.END);
                        else if (curr == '+') state.reset(TERMINAL.PLUS);
                        else if (curr == '-') state.reset(TERMINAL.MINUS);
                        else if (curr == '*') state.reset(TERMINAL.MULTI);
                        else if (curr == '/') state.reset(TERMINAL.DIVIDE);
                        else if (curr == ',') state.reset(TERMINAL.COMMA);
                        else if (curr == '^') state.reset(TERMINAL.POWER);
                        else if (curr == '{') state.reset(TERMINAL.L_BRACKET);
                        else if (curr == '}') state.reset(TERMINAL.R_BRACKET);
                        else if (curr == '\"') state.setState(14);
                        else throw new SyntaxError("Unexpeced Character: "+curr,lineNum);
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
                                else if (state.getToken().equals("if")) state.reset(TERMINAL.IF);
                                else if (state.getToken().equals("else")) state.reset(TERMINAL.ELSE);
                                else if (state.getToken().equals("while")) state.reset(TERMINAL.WHILE);
                                else if (state.getToken().equals("for")) state.reset(TERMINAL.FOR);
                                else state.reset(TERMINAL.ID);
                            }
                            continue;
                        }
                        else throw new SyntaxError("Unexpeced Character: "+curr,lineNum);

                    case 2:
                        if (!Character.isDigit(next) && Character.isDigit(curr)) state.reset(TERMINAL.DOUBLE);
                        else if(Character.isDigit(curr)) state.setState(4);
                        else throw new SyntaxError("Unexpeced Character: "+curr,lineNum);
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
                        else throw new SyntaxError("Unexpeced Character: "+curr,lineNum);
                        break;
                    case 4:
                        if(Character.isDigit(curr)){
                            if(!Character.isDigit(next))state.reset(TERMINAL.DOUBLE);
                            continue;
                        }
                        else throw new SyntaxError("Unexpeced Character: "+curr,lineNum);
                    case 5:
                        if (curr=='=') state.reset(TERMINAL.EQUAL);
                        else throw new SyntaxError("Expected = got "+curr,lineNum);
                        break;
                    case 14:
                        if(Character.isDigit(curr) || Character.isLetter(curr) || curr== ' ') continue;
                        else if (curr=='\"') state.reset(TERMINAL.STRING);
                        else throw new SyntaxError("Expected \" got "+curr,lineNum);
                        break;
                    case 16:
                        if (curr=='=') state.reset(TERMINAL.LESSEQUAL);
                        else throw new SyntaxError("Expected = got "+curr,lineNum);
                        break;
                    case 17:
                        if (curr=='=') state.reset(TERMINAL.GREATEREQUAL);
                        else throw new SyntaxError("Expected = got "+curr,lineNum);;
                        break;
                    case 18:
                        if (curr=='=') state.reset(TERMINAL.NOTEQUAL);
                        else throw new SyntaxError("Expected = got "+curr,lineNum);;
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

    public static void printTokens(List<List<Map.Entry<String, TERMINAL>>> tokens) {
        for (List<Map.Entry<String, TERMINAL>> line : tokens) {
            for (Map.Entry<String, TERMINAL> token : line) {
                System.out.print("[" + token.getKey() + "] ");
            }
            System.out.println();
        }
    }
    public static void printTerminals(List<List<Map.Entry<String, TERMINAL>>> tokens) {
        for (List<Map.Entry<String, TERMINAL>> line: tokens) {
            for (Map.Entry<String, TERMINAL> token: line) {
                System.out.print("" + token.getValue() + " ");
            }
            System.out.println();
        }
    }
}
