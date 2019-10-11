import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TreeInterpreter {
    public static List<TreeNode> findSTMTS(TreeNode tree,List<TreeNode> stmts){
        Collections.reverse(tree.children);
        for (Object t: tree.children){
            if(t.toString().equals("STMT")){
                stmts.add(getSTMTtype((TreeNode) t));
            }
            findSTMTS((TreeNode)t,stmts);
        }
        return stmts;
    }
    public static TreeNode getSTMTtype(TreeNode tree) {
        TreeNode stmt=(TreeNode) tree.children.get(0);
        if(!stmt.toString().equals("EXPR")){
            return stmt;
        }
        return stmt;
    }
    public static void handlePrint(TreeNode tree){
        TreeNode expr= (TreeNode) tree.children.get(2);
        Object exp= handleExpr(expr);
        System.out.println(exp);
    }
    public static Object handleExpr(TreeNode expr){

        TreeNode exprType= (TreeNode) expr.children.get(0);
        System.out.println(exprType);
        if (exprType.toString().equals("I_EXPR")) {
            List<String> i_exp=handleIntegerExpr(exprType);
            System.out.println(i_exp);
            int rh=Integer.parseInt(i_exp.get(0));
            int result=0;
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
                        rh=rh / Integer.parseInt(i_exp.get(i+1));
                        break;
                    case "^":
                        rh= (int) Math.pow(rh , Integer.parseInt(i_exp.get(i+1)));
                        break;
                }
            }
            return rh;
        }
        return expr;
    }

    public static List<String> handleIntegerExpr(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        System.out.println(child.get(0));
        if(child.get(0).toString().equals("ID")){
            return null;
        }
        else if(child.get(0).toString().equals("SIGN") && child.size()==2){
            if(((TreeNode) child.get(0)).children.size()!=0) {
                if (((TreeNode) child.get(0)).children.get(0).toString().equals("-")) {
                    expr.add("" + (Integer.parseInt(child.get(1).toString()) * -1));
                }
            }
            else expr.add(child.get(1).toString());
        }
        else if(child.get(3).toString().equals("SIGN")){

        }
        else if(child.get(3).toString().equals("I_EXPR")){

        }
        else if(child.get(0).toString().equals("I_EXPR")){

        }
        return expr;
    }
    public static String handleIntegerExpr2(TreeNode treeNode){
        List<TreeNode> child=treeNode.children;
        if(child.get(0).toString().equals("\'\'")){
            return "";
        }
        else{
            return handleOperation(child.get(0))+handleIntegerExpr(child.get(1))+handleIntegerExpr2(child.get(2));
        }
    }

    public static String handleOperation(TreeNode treeNode){
        return treeNode.children.get(0).toString();
    }



    public static boolean runTree(TreeNode tree){
        System.out.println();
        System.out.println("Tree:");
        System.out.println();
        TreeNode.printTree(tree);
        System.out.println();
        System.out.println("Statements:");
        List<TreeNode> statements = findSTMTS(tree,new ArrayList<>());
        Collections.reverse(statements);
        System.out.println(statements);
        for (TreeNode statement:statements) {
            System.out.println(statement);
            if (statement.toString().equals("PRINTSTMT")) {
                handlePrint(statement);
            } else if (statement.toString().equals("EXPR")) {
                handleExpr(statement);
            }
        }
        System.out.println("OUTPUT\n\n");

    return true;
    }
}
