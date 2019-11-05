import java.util.*;

public class TreeInterpreter {
    private static List<Map.Entry<String,Object>> ids= new ArrayList<>();
    public static List<TreeNode> findSTMTS(TreeNode tree,List<TreeNode> stmts){
        Collections.reverse(tree.children);
        for (Object t: tree.children){
            if(t.toString().equals("STMT")){
                stmts.add((TreeNode) t);
            }
            findSTMTS((TreeNode)t,stmts);
        }
        return stmts;
    }
    public static TreeNode getSTMTtype(TreeNode tree) {
        TreeNode stmt=(TreeNode) tree.children.get(0);
        System.out.println(tree.children);
        if(!stmt.toString().equals("EXPR")){
            return stmt;
        }
        return stmt;
    }
    public static void handlePrint(TreeNode tree){
        TreeNode expr= (TreeNode) tree.children.get(2);
        Object exp= handleExpr((TreeNode) expr.children.get(0));
        System.out.println(exp);
    }
    public static void handleAsmt(TreeNode tree){
        TreeNode expr= (TreeNode) tree.children.get(0);
        if(expr.toString().equals("INTASMT")){
            handleIntAsmt(expr);
        } else if(expr.toString().equals("DOUBLEASMT")){
            handleDoubleAsmt(expr);
        } else if(expr.toString().equals("STRASMT")){
            handleStrAsmt(expr);
        }
    }
    public static void handleIntAsmt(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        for (Map.Entry<String,Object> id:ids) {
            if(id.getKey().equals(child.get(1).toString())){
                id.setValue(handleExpr((TreeNode) treeNode.children.get(3)));
                return;
            }
        }
        Map.Entry<String,Object> id=new AbstractMap.SimpleEntry<>(child.get(1).toString(),(int)handleExpr((TreeNode) treeNode.children.get(3)));
        ids.add(id);
    }
    public static void handleDoubleAsmt(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        for (Map.Entry<String,Object> id:ids) {
            if(id.getKey().equals(child.get(1).toString())){
                id.setValue(handleExpr((TreeNode) treeNode.children.get(3)));
                return;
            }
        }
        Map.Entry<String,Object> id=new AbstractMap.SimpleEntry<>(child.get(1).toString(),handleExpr((TreeNode) treeNode.children.get(3)));
        ids.add(id);
    }
    public static void handleStrAsmt(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        for (Map.Entry<String,Object> id:ids) {
            if(id.getKey().equals(child.get(1).toString())){
                id.setValue(handleExpr((TreeNode) treeNode.children.get(3)));
                return;
            }
        }
        Map.Entry<String,Object> id=new AbstractMap.SimpleEntry<>(child.get(1).toString(),handleExpr((TreeNode) treeNode.children.get(3)));
        ids.add(id);
    }
    public static void handleReasmt(TreeNode tree){

    }
    public static Object handleExpr(TreeNode exprType){
        if (exprType.toString().equals("I_EXPR")) {
            List<String> i_exp=handleIntegerExpr(exprType);
            int rh=Integer.parseInt(i_exp.get(0));
            for(int i=1;i<i_exp.size();i+=2){
                switch (i_exp.get(i)){
                    case "+":
                        rh=rh + Integer.parseInt(i_exp.get(i+1));
                        break;
                    case "-":
                        rh=rh - Integer.parseInt(i_exp.get(i+1));
                        break;
                    case "*":
                        rh=rh * Integer.parseInt(i_exp.get(i+1));
                        break;
                    case "/":
                        int lh=Integer.parseInt(i_exp.get(i+1));
                        if (lh==0){
                            System.err.println("Runtime Error: Divide by zero");
                            System.exit(0);
                        }
                        rh=rh / lh;
                        break;
                    case "^":
                        rh= (int) Math.pow(rh , Integer.parseInt(i_exp.get(i+1)));
                        break;
                }
            }
            return rh;
        }
        else if (exprType.toString().equals("D_EXPR")) {
            List<String> d_exp=handleDoubleExpr(exprType);
            double rh=Double.parseDouble(d_exp.get(0));
            for(int i=1;i<d_exp.size();i+=2){
                switch (d_exp.get(i)){
                    case "+":
                        rh=rh + Double.parseDouble(d_exp.get(i+1));
                        break;
                    case "-":
                        rh=rh - Double.parseDouble(d_exp.get(i+1));
                        break;
                    case "*":
                        rh=rh * Double.parseDouble(d_exp.get(i+1));
                        break;
                    case "/":
                        double lh=Double.parseDouble(d_exp.get(i+1));
                        if (lh==0){
                            System.err.println("Runtime Error: Divide by zero");
                            System.exit(0);
                        }
                        rh=rh / lh;
                        break;
                    case "^":
                        rh=Math.pow(rh , Double.parseDouble(d_exp.get(i+1)));
                        break;
                }
            }
            return rh;
        }
        else if(exprType.toString().equals("S_EXPR")){
            return handleStringExpr(exprType);
        }
        else {
            return handleID(exprType);
        }
    }
    public static void handleIf(TreeNode i_expr,TreeNode b_stmtls){

    }
    public static void handleIfElse(TreeNode i_expr,TreeNode b_stmtls,TreeNode b_stmtls2){

    }
    public static void handleWhile(TreeNode i_expr,TreeNode b_stmtls){

    }
    public static void handleFor(TreeNode asmt,TreeNode i_expr,TreeNode reasmt,TreeNode b_stmtls){

    }
    public static List<String> handleDoubleExpr(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        if(child.size()==2&&child.get(1).toString().equals("D_EXPR2")){
            Object value=handleID((TreeNode) child.get(0));
            try{
                double idValue=(double)(value);
                expr.add(""+idValue);
            }
            catch(ClassCastException ex) {
                System.err.format("Syntax Error: Type mismatch: Expected Double got: "+handleID((TreeNode) child.get(0)).getClass().getSimpleName());
                System.exit(0);
            }
            expr.addAll(handleDoubleExpr2((TreeNode) child.get(1)));
        }
        else if(child.size()==2&&child.get(0).toString().equals("SIGN")){
            if(handleSign((TreeNode)child.get(0)).equals("-")) {
                expr.add("" + (Double.parseDouble(child.get(1).toString()) * -1));
            }
            else expr.add(child.get(1).toString());
        }
        else if(child.size()==5&&child.get(3).toString().equals("SIGN")){
            if(handleSign((TreeNode)child.get(0)).equals("-")) {
                expr.add("" + (Double.parseDouble(child.get(1).toString()) * -1));
            }
            else{
                expr.add(child.get(1).toString());
            }
            expr.add(handleOperation((TreeNode)child.get(2)));
            if(handleSign((TreeNode)child.get(3)).equals("-")) {
                expr.add("" + (Double.parseDouble(child.get(4).toString()) * -1));
            }
            else{
                expr.add(child.get(4).toString());
            }

        }
        else if(child.size()==4&&child.get(3).toString().equals("D_EXPR")){
            if(handleSign((TreeNode)child.get(0)).equals("-")) {
                expr.add("" + (Double.parseDouble(child.get(1).toString()) * -1));
            }
            else{
                expr.add(child.get(1).toString());
            }
            expr.add(handleOperation((TreeNode)child.get(2)));
            expr.addAll(handleDoubleExpr((TreeNode) child.get(3)));
        }
        else if(child.size()==3&&child.get(0).toString().equals("D_EXPR")){
            expr.addAll(handleDoubleExpr((TreeNode) child.get(0)));
            expr.add(handleOperation((TreeNode)child.get(1)));
            expr.addAll(handleDoubleExpr((TreeNode) child.get(2)));
        }
        else {
            System.err.println("Not suppose to be here");
            System.exit(0);
        }
        return expr;
    }
    public static List<String> handleDoubleExpr2(TreeNode treeNode){
        List<TreeNode> child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        if(child.size()==3){
            expr.add(handleOperation(child.get(0)));
            expr.addAll(handleDoubleExpr(child.get(1)));
            expr.addAll(handleDoubleExpr2(child.get(2)));
        }
        return expr;
    }


    public static List<String> handleIntegerExpr(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        if(child.size()==2&&child.get(1).toString().equals("I_EXPR2")){
            Object value=handleID((TreeNode) child.get(0));
            try{
                int idValue=(int)(value);
                expr.add(""+idValue);
            }
            catch(ClassCastException ex) {
                System.err.format("Type mismatch: Expected Integer got: "+handleID((TreeNode) child.get(0)).getClass().getSimpleName());
                System.exit(0);
            }
            expr.addAll(handleIntegerExpr2((TreeNode) child.get(1)));
        }
        else if(child.size()==2&&child.get(0).toString().equals("SIGN")){
            if(handleSign((TreeNode)child.get(0)).equals("-")) {
                expr.add("" + (Integer.parseInt(child.get(1).toString()) * -1));
            }
            else expr.add(child.get(1).toString());
        }
        else if(child.size()==5&&child.get(3).toString().equals("SIGN")){
            if(handleSign((TreeNode)child.get(0)).equals("-")) {
                expr.add("" + (Integer.parseInt(child.get(1).toString()) * -1));
            }
            else{
                expr.add(child.get(1).toString());
            }
            expr.add(handleOperation((TreeNode)child.get(2)));
            if(handleSign((TreeNode)child.get(3)).equals("-")) {
                expr.add("" + (Integer.parseInt(child.get(4).toString()) * -1));
            }
            else{
                expr.add(child.get(4).toString());
            }

        }
        else if(child.size()==4&&child.get(3).toString().equals("I_EXPR")){
            if(handleSign((TreeNode)child.get(0)).equals("-")) {
                expr.add("" + (Integer.parseInt(child.get(1).toString()) * -1));
            }
            else{
                expr.add(child.get(1).toString());
            }
            expr.add(handleOperation((TreeNode)child.get(2)));
            expr.addAll(handleIntegerExpr((TreeNode) child.get(3)));
        }
        else if(child.size()==3&&child.get(0).toString().equals("I_EXPR")){
            expr.addAll(handleIntegerExpr((TreeNode) child.get(0)));
            expr.add(handleOperation((TreeNode)child.get(1)));
            expr.addAll(handleIntegerExpr((TreeNode) child.get(2)));
        }
        return expr;
    }
    public static List<String> handleIntegerExpr2(TreeNode treeNode){
        List<TreeNode> child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        if(child.size()==3){
            expr.add(handleOperation(child.get(0)));
            expr.addAll(handleIntegerExpr(child.get(1)));
            expr.addAll(handleIntegerExpr2(child.get(2)));
        }
        return expr;
    }
    public static String handleStringExpr(TreeNode treeNode){
        List<TreeNode> child=treeNode.children;
        Collections.reverse(child);

        if (child.get(0).toString().charAt(0)=='\"'){
            return child.get(0).toString().substring(1,child.get(0).toString().length()-1);
        }
        else if(child.size()==1) {
            Object value=handleID(child.get(0));
            return (String) value;
        }
        else if (child.get(0).toString().equals("concat")){
            return handleStringExpr(child.get(2))+handleStringExpr(child.get(4));
        }
        else if (child.get(0).toString().equals("charAt")){
            String s=handleStringExpr(child.get(2));
            int index= (int) handleExpr(child.get(4));
            return s.charAt(index)+"";
        }
        return null;
    }
    public static String handleSign(TreeNode treeNode){
        if(treeNode.children.size()!=0) {
            return treeNode.children.get(0).toString().equals("-") ? "-" : "";
        }
        return "";
    }

    public static String handleOperation(TreeNode treeNode){
        return treeNode.children.get(0).toString();
    }
    public static Object handleID(TreeNode treeNode){
        String id=treeNode.toString();
        for (Map.Entry<String,Object> o:ids ) {
            if(o.getKey().equals(id)){
                return o.getValue();
            }
        }
        System.err.format("Undeclared Variable:%s",id);
        return null;
    }

    public static void runTree(TreeNode tree){
        List<TreeNode> statements = findSTMTS(tree,new ArrayList<>());
        Collections.reverse(statements);
        System.out.println(statements+"\n");
        for (TreeNode statement:statements) {
            Collections.reverse(statement.children);
            System.out.println(statement+"   "+statement.children);
            if (statement.children.get(0).toString().equals("PRINTSTMT")) {
                handlePrint((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("ASMT")) {
                handleAsmt((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("REASMT")) {
                handleReasmt((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("EXPR")) {
                handleExpr((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("if")&& statement.children.get(7).toString().equals("else")) {
                handleIfElse((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5),(TreeNode) statement.children.get(9));
            } else if (statement.children.get(0).toString().equals("if")){
                handleIf((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5));
            }else if (statement.children.get(0).toString().equals("for")){
                handleFor((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(3),(TreeNode) statement.children.get(5),(TreeNode) statement.children.get(8));
            }else if (statement.children.get(0).toString().equals("while")){
                handleWhile((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5));
            }
        }
    }
}
