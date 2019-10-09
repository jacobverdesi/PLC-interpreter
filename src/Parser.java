import java.time.temporal.Temporal;
import java.util.*;

public class Parser {
    public static void printTable (List<List<String>> matrix){
        for(List<String> line: matrix){
            for(Object state:line){
                System.out.printf("[%10s] ",  state);
            }
            System.out.println();
        }
    }
    private static void parseError(int line , int column, TERMINAL curr){
        System.err.format("Invalid syntax line: %s column: %s character: %s\n", line, column,curr);
        System.exit(0);
    }

    public static List<List<String>> parseTable(List<String> lines){
        List<List<String>> matrix = new ArrayList<>();
        List<String> prim= Arrays.asList(lines.get(0).strip().split("\t"));
        matrix.add(prim);
        for(int i=1;i<lines.size();i++){
            String line=lines.get(i).strip();
            List<String> states=new ArrayList<String>(Collections.nCopies(prim.size(), ""));
            List<String>temp=Arrays.asList(line.split("\t"));
            for(int j=0;j<temp.size();j++){
                states.set(j,temp.get(j));
            }
            matrix.add(states);
        }
        return matrix;
    }

    public static List<Integer> parseTree(List<String> rules,List<List<String>> parseTable, List<List<Map.Entry<String, TERMINAL>>> tokens){
        List<TERMINAL> tokenList=new ArrayList<>();
        for (List<Map.Entry<String,TERMINAL>> t:tokens) {
            for (Map.Entry<String, TERMINAL> m:t) {
                tokenList.add(m.getValue());
            }
        }
        tokenList.add(TERMINAL.$);
        Stack<Integer> stack=new Stack<>();
        List<Integer> output=new ArrayList<>();
        String action="";
        int state=0;
        int tokenIndex=0;
        int step=0;
        stack.push(0);
        while (true){
            TERMINAL currToken= tokenList.get(tokenIndex);
            step++;
            action = parseTable.get(stack.peek() +1).get(parseTable.get(0).indexOf(currToken.toString()));
            //System.out.printf("[%2d] %-60s %-70s [%3s]\n",step,stack,tokenList.subList(tokenIndex,tokenList.size()),action);
            if(action.equals("acc")) break;
            if(action.equals("")){
                parseError(0,0,currToken);
                return null;
            }
            char shiftReduce = action.charAt(0);
            state=Integer.parseInt(action.substring(1));
            if(shiftReduce=='s'){
                stack.push(state);
                tokenIndex++;
            }
            else if (shiftReduce=='r'){
                String[] rightSide=rules.get(state).substring(rules.get(state).indexOf("->")+3).split(" ");
                for (String s : rightSide) {
                    if (!s.equals("\'\'")) stack.pop();
                }
                output.add(state);
                String rule=rules.get(state).split(" ")[0];
                int newState=Integer.parseInt(parseTable.get(stack.peek()+1).get(parseTable.get(0).indexOf(rule)));
                stack.push(newState);
                //System.out.println(stack);
            }
        }
        return output;
    }
}
