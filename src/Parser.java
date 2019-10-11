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
    private static int stateIndex(Stack stack) {

        return (int)stack.get(2 * ((stack.size() - 1) >> 1))+1;
    }
    public static char actionElement(String action){
        return (action.charAt(0)=='r'||action.charAt(0)=='s') ? action.charAt(0) : ' ';
    }
    private static int actionIndex(String action){
        if(actionElement(action)==' '){
            return Integer.parseInt(action);
        }
        else if(actionElement(action)=='s'){
            return Integer.parseInt(action.substring(1));
        }
        else return -1;
    }

    public static TreeNode parseTree(List<String> rules,List<List<String>> parseTable, List<List<Map.Entry<String, TERMINAL>>> tokens){
        List<TERMINAL> tokenList=new ArrayList<>();
        for (List<Map.Entry<String,TERMINAL>> t:tokens) {
            for (Map.Entry<String, TERMINAL> m:t) {
                tokenList.add(m.getValue());
            }
        }
        tokenList.add(TERMINAL.$);

        Stack stack=new Stack<>();
        stack.push(0);

        int tokenIndex=0;
        TERMINAL currToken= tokenList.get(tokenIndex);
        List<String> state=parseTable.get(stateIndex(stack));
        String action=state.get(parseTable.get(0).indexOf(currToken.toString()));
        char actionType=actionElement(action);
        int actionIndex = actionIndex(action);
        int step=1;
        System.out.printf("[%2d] %-60s %-70s [%3s]\n",step++,stack,tokenList.subList(tokenIndex,tokenList.size()),action);

        while (!action.equals("acc") ){
            if(actionType=='s'){
                stack.push(currToken);
                stack.push(actionIndex);
                tokenIndex++;
            }
            else if (actionType=='r'){
                String[] rightSide=rules.get(Integer.parseInt(action.substring(1))).substring(rules.get(Integer.parseInt(action.substring(1))).indexOf("->")+3).split(" ");
                List removed=new ArrayList();
                for (String s : rightSide) {
                    if (!s.equals("\'\'")) {
                        stack.pop();
                        removed.add(stack.pop());
                    }
                }
                String rule=rules.get(Integer.parseInt(action.substring(1))).split(" ")[0];
                TreeNode node=new TreeNode<>(rule);
                System.out.println("******");
                System.out.println("Root: "+node);
                System.out.println("Removed List: "+removed);
                for(int i=0;i<removed.size();i++){
                    if(removed.get(i) instanceof TreeNode){
                        //System.out.println(((TreeNode) removed.get(i)).children.getClass());
                        System.out.println(removed.get(i));
                        for (Object o :((TreeNode) removed.get(i))) {
                            TreeNode child = (TreeNode) o;
                            node.addChild(child);
                        }

                    }
                    else {
                        node.addChild(removed.get(i).toString());
                    }
                }
                //System.out.println(node.children);
//                System.out.println("************");
                TreeNode.printTree(node);
//                System.out.println("-------------");

                stack.push(node);
            }
            else {
                stack.push(actionIndex);
            }
            state=parseTable.get(stateIndex(stack));
            currToken= tokenList.get(tokenIndex);
            //System.out.printf("%s %s %s\n",state,stack,currToken);
            if(actionIndex==-1) action=state.get(parseTable.get(0).indexOf(stack.peek().toString()));
            else action=state.get(parseTable.get(0).indexOf(currToken.toString()));
            if (!action.equals("acc")) {
                actionType = actionElement(action);
                actionIndex = actionIndex(action);
            }
            //System.out.printf("[%2d] %-60s %-70s [%3s]\n",step,stack,tokenList.subList(tokenIndex,tokenList.size()),action);
            if(action.equals("")){
                parseError(0,0,currToken); //TODO get the lines and things
            }
            step++;

        }
        stack.pop();
        Object tree=stack.pop();
        return  (TreeNode) tree;
    }
}
