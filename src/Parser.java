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
    public static List<List<String>> parseTable(List<String> lines){
        List<List<String>> matrix = new ArrayList<>();
        List<String> prim= Arrays.asList(lines.get(0).strip().split("\t"));
        matrix.add(prim);
        for(int i=1;i<lines.size();i++){
            String line=lines.get(i).strip();
            List<String> states=new ArrayList<String>(Collections.nCopies(prim.size(), ""));
            List<String>temp=Arrays.asList(line.split("\t"));
            for(String state:temp){
                states.set(temp.indexOf(state),state);
            }
            matrix.add(states);
        }
        return matrix;
    }

    public static Boolean parseTree(List<String> rules,List<List<String>> parseTable, List<List<Map.Entry<String, TERMINAL>>> tokens){
        Stack<Integer> stack=new Stack<>();
        List<Integer> output=new ArrayList<>();


        for (List<Map.Entry<String, TERMINAL>> line: tokens) {
            int step=0;
            stack.clear();
            stack.push(0);
            for (int i=0;i<line.size();i++) {
                Map.Entry<String, TERMINAL> curr= line.get(i);
                step++;
                String action = parseTable.get(stack.peek() +1).get(parseTable.get(0).indexOf(curr.getValue().toString()));
                System.out.println(step+" "+ stack+ line.subList(line.indexOf(curr),line.size())+ " "+ action+ " ");
                if(action.equals("acc")) {
                    break;
                }
                if(action.equals("")){
                    return false;
                }
                else{
                    char shiftReduce = action.charAt(0);
                    int state=Integer.parseInt(action.substring(1));
                    if(shiftReduce=='s'){
                        stack.push(state);
                    }
                    else if (shiftReduce=='r'){
                        String[] rightSide=rules.get(state).substring(rules.get(state).indexOf("->")+3).split(" ");
                        for (String s : rightSide) {
                            if (!s.equals("\'\'")) stack.pop();
                        }
                        String rule=rules.get(state).split(" ")[0];
                        int newState=Integer.parseInt(parseTable.get(stack.peek()+1).get(parseTable.get(0).indexOf(rule)));
                        stack.push(newState);
                        System.out.println(stack);
                        i--;
                    }
                }
            }
            System.out.println();
        }
        return true;
    }
}
