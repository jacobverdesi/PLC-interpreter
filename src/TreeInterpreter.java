import com.sun.source.tree.Tree;

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
    public static void handlePrint(TreeNode tree){
        Collections.reverse(tree.children);
        TreeNode expr= (TreeNode) tree.children.get(2);
        Object exp= handleExpr((TreeNode) expr.children.get(0));
        System.out.println(exp);
    }
    public static void handleAsmt(TreeNode tree){
        TreeNode expr= (TreeNode) tree.children.get(0);
        List child=expr.children;
        Collections.reverse(child);
        Map.Entry<String,Object> id=new AbstractMap.SimpleEntry<>(child.get(1).toString(), handleExpr((TreeNode) child.get(3)));
        ids.add(id);
    }

    public static void handleReasmt(TreeNode tree){
        List child=tree.children;
        Collections.reverse(child);
        int index=-1;
        for (Map.Entry<String,Object> o:ids ) {
            if(o.getKey().equals(child.get(0).toString())){
                index=ids.indexOf(o);
            }
        }
        if(index!=-1) {
            ids.get(index).setValue(handleExpr((TreeNode) child.get(2)));
        }
        else {
            System.err.format("Undeclared Variable:%s",child.get(0).toString());
            System.exit(0);
        }
    }
    public static Object handleExpr(TreeNode exprType){
        if (exprType.toString().equals("I_EXPR")) {
            List<String> i_exp=handleIntegerExpr(exprType);
            int lh = Integer.parseInt(i_exp.get(0));
            for(int i=1;i<i_exp.size();i+=2){
                int compare = (lh + "").compareTo(i_exp.get(i + 1) + "");
                switch (i_exp.get(i)){
                    case "+": lh=lh + Integer.parseInt(i_exp.get(i+1));break;
                    case "-": lh=lh - Integer.parseInt(i_exp.get(i+1));break;
                    case "*": lh=lh * Integer.parseInt(i_exp.get(i+1));break;
                    case "/":
                        if (Integer.parseInt(i_exp.get(i+1))==0){
                            System.err.println("Runtime Error: Divide by zero");
                            System.exit(0);
                        }
                        lh=lh / Integer.parseInt(i_exp.get(i+1));
                        break;
                    case "^": lh= (int) Math.pow(lh , Integer.parseInt(i_exp.get(i+1)));break;
                    case "<": lh= ((compare<0) ? 1 : 0);break;
                    case ">": lh= ((compare>0) ? 1 : 0);break;
                    case "==": lh= ((compare==0) ? 1 : 0);break;
                    case "!=": lh= ((compare!=0) ? 1 : 0);break;
                    case "<=": lh= ((compare<=0) ? 1 : 0);break;
                    case ">=": lh= ((compare>=0) ? 1 : 0);break;
                }
            }
            return lh;
        }
        else if (exprType.toString().equals("D_EXPR")) {
            List<String> d_exp=handleDoubleExpr(exprType);
            double lh=Double.parseDouble(d_exp.get(0));
            for(int i=1;i<d_exp.size();i+=2){
                double rh=Double.parseDouble(d_exp.get(i+1));
                switch (d_exp.get(i)){
                    case "+": lh = lh + rh;break;
                    case "-": lh = lh - rh;break;
                    case "*": lh = lh * rh;break;
                    case "/":
                        if (rh==0){
                            System.err.println("Runtime Error: Divide by zero");
                            System.exit(0);
                        }
                        lh=lh / rh;
                        break;
                    case "^": lh = Math.pow(lh , rh);break;
                }
            }
            return lh;
        }
        else if(exprType.toString().equals("S_EXPR")){
            return handleStringExpr(exprType);
        }
        else {
            return null;
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
        if(child.get(0).toString().equals("SIGN")){
            String sign=handleSign((TreeNode) child.get(0));
            double value=Double.parseDouble(child.get(1).toString());
            if(sign.equals("-")) value=value*-1;
            expr.add(""+value);
            expr.addAll(handleDoubleExpr2((TreeNode) child.get(2)));
        } else if(child.size()==2&&child.get(1).toString().equals("D_EXPR2")){
            expr.add(""+handleID((TreeNode) child.get(0)));
            expr.addAll(handleDoubleExpr2((TreeNode) child.get(1)));
        }
        return expr;
    }
    public static List<String> handleDoubleExpr2(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        if(child.size()==2){
            expr.add(handleOperation((TreeNode) child.get(0)));
            expr.addAll(handleDoubleExpr((TreeNode) child.get(1)));
        }
        return expr;
    }
    public static List<String> handleIntegerExpr(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        if(child.get(0).toString().equals("B_EXPR")){
            expr.add(handleBooleanExpr((TreeNode)child.get(0))+"");
            expr.addAll(handleIntegerExpr3((TreeNode)child.get(1)));
        } else if(child.get(0).toString().equals("I_EXPR2")){
            expr.addAll(handleIntegerExpr2((TreeNode) child.get(0)));
            expr.addAll(handleIntegerExpr3((TreeNode)child.get(1)));

        }
        return expr;
    }
    public static List<String> handleIntegerExpr2(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();

        if(child.get(0).toString().equals("SIGN")){
            String sign=handleSign((TreeNode) child.get(0));
            int value=Integer.parseInt(child.get(1).toString());
            if(sign.equals("-")) value=value*-1;
            expr.add(""+value);
        }
        else if(child.size()==1){
            expr.add(handleID((TreeNode) child.get(0)).toString());
        }
        else if(child.get(0).toString().equals("I_EXPR2")){
            expr.addAll(handleIntegerExpr2((TreeNode)child.get(0)));
            expr.add(((TreeNode)child.get(1)).children.get(0).toString());
            expr.addAll(handleIntegerExpr2((TreeNode)child.get(2)));
        }
        return expr;
    }
    public static List<String> handleIntegerExpr3(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        if(child.size()==2){
            expr.add(handleOperation((TreeNode) child.get(0)));
            expr.addAll(handleIntegerExpr2((TreeNode) child.get(1)));
        }
        return expr;
    }
    public static String handleStringExpr(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        if (child.get(0).toString().charAt(0)=='\"'){
            return child.get(0).toString().substring(1,child.get(0).toString().length()-1);
        }
        else if(child.size()==1) {
            Object value=handleID((TreeNode) child.get(0));
            return (String) value;
        }
        else if (child.get(0).toString().equals("concat")){
            return handleStringExpr((TreeNode) child.get(2))+handleStringExpr((TreeNode) child.get(4));
        }
        else if (child.get(0).toString().equals("`charAt")){
            return handleStringExpr((TreeNode)child.get(2)).charAt((int) handleExpr((TreeNode)child.get(4)))+"";
        }
        return "";
    }
    public static int handleBooleanExpr(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        int compare;
        if(child.get(0).toString().equals("S_EXPR")){
            compare = (handleStringExpr((TreeNode) child.get(0))).compareTo(handleStringExpr((TreeNode) child.get(2)));
        }
        else{
            double lh= (double) handleExpr((TreeNode) child.get(0));
            double rh ;
            if(child.size()==3) {
                rh = (double) handleID((TreeNode) child.get(2));
            }
            else {
                String sign=handleSign((TreeNode) child.get(2));
                rh=Double.parseDouble(child.get(3).toString());
                if(sign.equals("-")) rh=rh*-1;
            }
            compare = (lh + "").compareTo(rh+ "");

        }
        switch (((TreeNode)child.get(1)).children.get(0).toString()) {
            case "<": return ((compare < 0) ? 1 : 0);
            case ">": return ((compare > 0) ? 1 : 0);
            case "==": return ((compare == 0) ? 1 : 0);
            case "!=": return ((compare != 0) ? 1 : 0);
            case "<=": return ((compare <= 0) ? 1 : 0);
            case ">=": return ((compare >= 0) ? 1 : 0);
        }
        return 0;
    }
    public static String handleSign(TreeNode treeNode){
            return treeNode.children.size()!=0&&treeNode.children.get(0).toString().equals("-") ? "-" : "";
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
        System.exit(0);
        return null;
    }

    public static void runTree(TreeNode tree){
        List<TreeNode> statements = findSTMTS(tree,new ArrayList<>());
        Collections.reverse(statements);
        for (TreeNode statement:statements) {
            Collections.reverse(statement.children);
            if (statement.children.get(0).toString().equals("PRINTSTMT")) {
                handlePrint((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("ASMT")) {
                handleAsmt((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("REASMT")) {
                handleReasmt((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("EXPR")) {
                handleExpr((TreeNode) ((TreeNode)statement.children.get(0)).children.get(0));
            } else if (statement.children.get(0).toString().equals("if")&& statement.children.size()== 11 && statement.children.get(7).toString().equals("else")) {
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
