import java.util.*;

public class TreeInterpreter {
    private static List<Map.Entry<String,Object>> ids= new ArrayList<>();
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
                        rh=rh / Integer.parseInt(i_exp.get(i+1));
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
                        rh=rh / Double.parseDouble(d_exp.get(i+1));
                        break;
                    case "^":
                        rh= (int) Math.pow(rh , Double.parseDouble(d_exp.get(i+1)));
                        break;
                }
            }
            return rh;
        }
        return expr;
    }
    public static List<String> handleDoubleExpr(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        if(child.size()==2&&child.get(0).toString().equals("ID")){
            try{
                double idValue=Double.parseDouble(handleID((TreeNode) child.get(1)));
                expr.add(""+idValue);
            }
            catch(NumberFormatException ex) {
                System.err.format("Type mismatch: Expected Double got:"+handleID((TreeNode) child.get(1)).getClass());
                System.exit(0);
            }
            expr.add(handleDoubleExpr2((TreeNode) child.get(1)));
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
        return expr;
    }
    public static String handleDoubleExpr2(TreeNode treeNode){
        List<TreeNode> child=treeNode.children;
        if(child.get(0).toString().equals("\'\'")){
            return "";
        }
        else{
            return handleOperation(child.get(0))+handleDoubleExpr(child.get(1))+handleDoubleExpr2(child.get(2));
        }
    }

    public static List<String> handleIntegerExpr(TreeNode treeNode){
        List child=treeNode.children;
        Collections.reverse(child);
        List<String> expr=new ArrayList<>();
        if(child.size()==2&&child.get(0).toString().equals("ID")){
            try{
                int idValue=Integer.parseInt(handleID((TreeNode) child.get(1)));
                expr.add(""+idValue);
            }
            catch(NumberFormatException ex) {
                System.err.format("Type mismatch: Expected Integer got:"+handleID((TreeNode) child.get(1)).getClass());
                System.exit(0);
            }
            expr.add(handleIntegerExpr2((TreeNode) child.get(1)));
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
    public static String handleIntegerExpr2(TreeNode treeNode){
        List<TreeNode> child=treeNode.children;
        if(child.get(0).toString().equals("\'\'")){
            return "";
        }
        else{
            return handleOperation(child.get(0))+handleIntegerExpr(child.get(1))+handleIntegerExpr2(child.get(2));
        }
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
    public static String handleID(TreeNode treeNode){
        String id=treeNode.children.get(0).toString();
        for (Map.Entry<String,Object> o:ids ) {
            if(o.getKey().equals(id)){
                return (String) o.getValue();
            }
        }
        return null;
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
        System.out.println(statements+"\n");
        System.out.println("OUTPUT");
        for (TreeNode statement:statements) {
            if (statement.toString().equals("PRINTSTMT")) {
                handlePrint(statement);
            } else if (statement.toString().equals("EXPR")) {
                handleExpr(statement);
            }
        }


    return true;
    }
}
