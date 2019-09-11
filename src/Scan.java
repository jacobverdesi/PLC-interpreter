
import java.io.*;
import java.util.*;

import java.util.logging.Logger;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isLetter;

public class Scan {

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
            StringBuilder curr= new StringBuilder();
            DFAstate state=DFAstate.Q0;
            char[] chars=line.toCharArray();
            for (int i=0; i < chars.length;i++){
                Character c=chars[i];
                char ahead=' ';
                if(i<chars.length-1) {
                    ahead = chars[i + 1];
                }

                System.out.println("i: "+i+" current: "+c+" ahead: "+ahead + " state: "+state);

                if(state==DFAstate.Q0){
                    if(c==' '|| c=='\n'){
                        state=DFAstate.Q0;
                    }
                    else if(Character.isLetter(c)){
                        curr.append(c);
                        state=DFAstate.Q1;
                    }
                    else if(c=='.'){
                        curr.append(c);
                        state=DFAstate.Q2;
                    }
                    else if(Character.isDigit(c)){
                        curr.append(c);
                        state=DFAstate.Q3;
                    }
                    else if(c=='='||c=='('||c==')'||c==';'||c=='+'||c=='-'||c=='*'||c=='/'||c=='^') {
                        tokens.add(c.toString());
                    }
                    else if(c=='"') {
                        state = DFAstate.Q14;
                    }
                    else{
                        System.err.format("Invalid syntax line = %s \n",lineNum);
                    }
                }
                else if(state==DFAstate.Q1){
                    if(Character.isLetter(c)||Character.isDigit(c)) {
                        curr.append(c);
                        if (!Character.isLetter(ahead) && !Character.isDigit(ahead)) {
                            System.out.println("test");
                            tokens.add(curr.toString());
                            curr.delete(0, curr.length());
                            state = DFAstate.Q0;
                        }
                    }
                }
                else if(state==DFAstate.Q2){
                    if(Character.isDigit(c)){
                        curr.append(c);
                    }
                    else {
                        System.err.format("Invalid syntax line = %s\n",lineNum);
                    }
                }
                else if(state==DFAstate.Q3){
                    if(Character.isDigit(c)){
                        curr.append(c);
                    }
                    else if(c=='.'){
                        curr.append(c);
                        state=DFAstate.Q4;
                    }
                    else if((ahead!='.'||!Character.isDigit(ahead))){
                        tokens.add(curr.toString());
                        curr.delete(0,curr.length());
                        state=DFAstate.Q0;
                    }
                    else {
                        System.err.format("Invalid syntax line = %s\n",lineNum);
                    }
                }
                else if(state==DFAstate.Q4){
                    if(Character.isDigit(c)){
                        curr.append(c);
                    }
                    else if(!Character.isDigit(ahead)){
                        tokens.add(curr.toString());
                        curr.delete(0,curr.length());
                        state=DFAstate.Q0;
                    }
                    else {
                        System.err.format("Invalid syntax line = %s\n",lineNum);
                    }
                }
                else {
                    System.err.format("Invalid syntax line = %s\n",lineNum);
                }
            }
            lineNum++;
        }

        return tokens;
    }

    public static void printList(List<String> tokens){
        for (String token:tokens) {
            if (token.equals(";")) {
                System.out.println(token);
            } else {
                System.out.print("["+token+"] ");
            }
        }

    }


    public static void main(String[] args){
        List<String> a = readFile("src/program1.j");
        List<String> tokens=tokenizer(a);
        printList(tokens);


    }
}
