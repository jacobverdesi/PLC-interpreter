

import java.util.*;

public class TreeInterpreter {
    private static Dictionary ids2=new Hashtable();
    private static Dictionary functions = new Hashtable();
    public static void rmDic(Dictionary d){
        for(Iterator iterator = d.keys().asIterator(); iterator.hasNext();){
            d.remove(iterator.next());
        }
    }
    public static List<TreeNode> findSTMTS(TreeNode tree,List<TreeNode> stmts){
        for (Object t: tree.children){
            if(t.toString().equals("STMT")){
                stmts.add((TreeNode) t);
            }
            else if(t.toString().equals("STMT_LIST")){
                findSTMTS((TreeNode)t,stmts);
            }
        }
        return stmts;
    }
    public static List<TreeNode> findB_STMTS(TreeNode tree,List<TreeNode> stmts){
        for (Object t: tree.children){
            if(t.toString().equals("B_STMT")){
                stmts.add((TreeNode) t);
            }
            else if(t.toString().equals("B_STMT_LIST")){
                findB_STMTS((TreeNode)t,stmts);
            }
        }
        return stmts;
    }
    public static void handleB_STMT(TreeNode tree) throws RuntimeError {
        List<TreeNode> statements = findB_STMTS(tree,new ArrayList<>());
        for (TreeNode statement:statements) {
            if (statement.children.get(0).toString().equals("PRINTSTMT")) {
                handlePrint((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("REASMT")) {
                handleReasmt((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("EXPR")) {
                handleExpr((TreeNode)statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("if")&& statement.children.size()== 11 && statement.children.get(7).toString().equals("else")) {
                handleIfElse((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5),(TreeNode) statement.children.get(9));
            } else if (statement.children.get(0).toString().equals("if")){
                handleIf((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5));
            } else if (statement.children.get(0).toString().equals("for")){
                handleFor((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(3),(TreeNode) statement.children.get(5),(TreeNode) statement.children.get(8));
            } else if (statement.children.get(0).toString().equals("while")){
                handleWhile((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5));
            }else if (statement.children.size()>1 && statement.children.get(2).toString().equals("F_CALL_P_LIST")){
                handleVoidFCall((TreeNode) statement.children.get(0),(TreeNode) statement.children.get(2));
            }
        }
    }
    public static List<TreeNode> findF_STMTS(TreeNode tree,List<TreeNode> stmts){
        for (Object t: tree.children){
            if(t.toString().equals("F_STMT")){
                stmts.add((TreeNode) t);
            }
            else if(t.toString().equals("F_STMT_LIST")){
                findF_STMTS((TreeNode)t,stmts);
            }
        }
        return stmts;
    }
    public static void handleF_STMT(TreeNode tree) throws RuntimeError {
        List<TreeNode> statements = findF_STMTS(tree,new ArrayList<>());
        for (TreeNode statement:statements) {
            if (statement.children.get(0).toString().equals("PRINTSTMT")) {
                handlePrint((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("ASMT")) {
                handleAsmt((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("REASMT")) {
                handleReasmt((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("EXPR")) {
                handleExpr((TreeNode)statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("if")&& statement.children.size()== 11 && statement.children.get(7).toString().equals("else")) {
                handleIfElse((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5),(TreeNode) statement.children.get(9));
            } else if (statement.children.get(0).toString().equals("if")){
                handleIf((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5));
            } else if (statement.children.get(0).toString().equals("for")){
                handleFor((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(3),(TreeNode) statement.children.get(5),(TreeNode) statement.children.get(8));
            } else if (statement.children.get(0).toString().equals("while")){
                handleWhile((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5));
            }
        }
    }
    public static void handlePrint(TreeNode tree) throws RuntimeError {
        System.out.println(handleExpr((TreeNode) tree.children.get(2)));
    }
    public static void handleAsmt(TreeNode tree) throws RuntimeError {
        TreeNode expr= (TreeNode) tree.children.get(0);
        List child=expr.children;
        Map.Entry<String,Object> id=new AbstractMap.SimpleEntry<>(child.get(1).toString(), handleExprType((TreeNode) child.get(3)));
        ids2.put(child.get(1).toString(), handleExprType((TreeNode) child.get(3)));
    }

    public static void handleReasmt(TreeNode tree) throws RuntimeError {
        List child=tree.children;
        if(ids2.get(child.get(0).toString())!=null) {
            ids2.put(child.get(0).toString(),handleExprType((TreeNode)child.get(2)));
        }
        else {
            rmDic(ids2);
            rmDic(functions);
            throw new RuntimeError("Undelared Variable: "+child.get(0).toString(),0);
        }
    }
    public static void handleFunc(TreeNode expr) {

        List child=expr.children;

        List<Map.Entry<String, Object>> fids = new ArrayList<>(handleParameters((TreeNode) child.get(3)));
        List<Object> data;
        if(expr.children.size()==8){
             data= new ArrayList<>(Arrays.asList(child.get(0), fids, expr.children.get(6)));
        }
        else {
            data = new ArrayList<>(Arrays.asList(child.get(0), fids, expr.children.get(6), expr.children.get(8)));
        }
        functions.put(child.get(1).toString(),data);

    }
    public static Object handleFCall(TreeNode func,TreeNode parameters) throws RuntimeError{
        Dictionary temp=new Hashtable((Map) ids2);
        List<Map.Entry<String, Object>> vars=(List)((List)functions.get(func.toString())).get(1);
        List<Object> vars2= new ArrayList<>();
        vars2.addAll(handleF_CALL_P(parameters));
        int index=0;
        for (Map.Entry<String, Object> t : vars) {
            ids2.put(t.getKey(),vars2.get(index));
            index++;
        }
        handleF_STMT((TreeNode) ((List) functions.get(func.toString())).get(2));

        Object returns =handleExprType((TreeNode) (((List)functions.get(func.toString())).get(3)));
        for (Map.Entry<String, Object> t : vars) {
            ids2.remove(t.getKey());
        }
        for (Iterator iterator = temp.keys().asIterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if(ids2.get(key)==null){
                ids2.put(key,temp.get(key));
            }
        }
        return returns;
    }
    public static void handleVoidFCall(TreeNode func,TreeNode parameters) throws RuntimeError {
        Dictionary temp=new Hashtable((Map) ids2);
        List<Map.Entry<String, Object>> vars=(List)((List)functions.get(func.toString())).get(1);
        List<Object> vars2= new ArrayList<>();
        vars2.addAll(handleF_CALL_P(parameters));
        int index=0;
        for (Map.Entry<String, Object> t : vars) {
            ids2.put(t.getKey(),vars2.get(index));
            index++;
        }
        handleF_STMT((TreeNode) ((List) functions.get(func.toString())).get(2));
        for (Map.Entry<String, Object> t : vars) {
            ids2.remove(t.getKey());
        }
        for (Iterator iterator = temp.keys().asIterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if(ids2.get(key)==null){
                ids2.put(key,temp.get(key));
            }
        }
    }
    public static List<Object> handleF_CALL_P(TreeNode expr) throws RuntimeError{
        List<Object> list=new ArrayList<>();
        if(expr.children.size()==2){
            list.add(handleExpr((TreeNode) expr.children.get(0)));
            list.addAll(handleF_CALL_P2((TreeNode) expr.children.get(1)));
        }
        return list;
    }
    public static List<Object> handleF_CALL_P2(TreeNode expr) throws RuntimeError{
        List<Object> list=new ArrayList<>();
        if(expr.children.size()==3){
            list.add(handleExpr((TreeNode) expr.children.get(1)));
            list.addAll(handleF_CALL_P2((TreeNode) expr.children.get(2)));
        }
        return list;
    }
    public static void handleIf(TreeNode expr,TreeNode b_stmtls) throws RuntimeError {
        if(!handleExpr(expr).equals(0)){
            handleB_STMT(b_stmtls);
        }
    }
    public static List<Map.Entry<String,Object>> handleParameters (TreeNode expr){
        List<Map.Entry<String,Object>> fids= new ArrayList<>();
        if(expr.children.size()==1){
            fids.addAll(handleParameters2((TreeNode) expr.children.get(0)));
        }
        return fids;
    }
    public static List<Map.Entry<String,Object>> handleParameters2 (TreeNode expr){
        List<Map.Entry<String,Object>> fids= new ArrayList<>();
        Map.Entry<String,Object> p= new AbstractMap.SimpleEntry<>(expr.children.get(1).toString(),expr.children.get(0));
        fids.add(p);
        fids.addAll(handleParameters3((TreeNode) expr.children.get(2)));
        return fids;
    }
    public static List<Map.Entry<String,Object>> handleParameters3 (TreeNode expr){
        List<Map.Entry<String,Object>> fids= new ArrayList<>();
        if(expr.children.size()==2){
            fids.addAll(handleParameters2((TreeNode) expr.children.get(1)));
        }
        return fids;
    }

    public static void handleIfElse(TreeNode expr,TreeNode b_stmtls,TreeNode b_stmtls2) throws RuntimeError {
        if(!handleExpr(expr).equals(0)){
            handleB_STMT(b_stmtls);
        }
        else{
            handleB_STMT(b_stmtls2);
        }
    }
    public static void handleWhile(TreeNode i_expr,TreeNode b_stmtls) throws RuntimeError {
        while ((int)handleExprType(i_expr)!=0){
            handleB_STMT(b_stmtls);
        }
    }
    public static void handleFor(TreeNode asmt,TreeNode i_expr,TreeNode reasmt,TreeNode b_stmtls) throws RuntimeError {
        handleAsmt(asmt);
        while ((int)handleExprType(i_expr)!=0){
            handleB_STMT(b_stmtls);
            handleReasmt(reasmt);
        }
    }
    public static Object handleExpr(TreeNode tree) throws RuntimeError{
        return handleExprType((TreeNode) tree.children.get(0));
    }
    public static Object handleExprType(TreeNode exprType) throws RuntimeError{
        if (exprType.toString().equals("I_EXPR")) {
            List<String> i_exp=handleIntegerExpr(exprType);

            int lh = Integer.parseInt(i_exp.get(0));
            for(int i=1;i<i_exp.size();i+=2){
                int rh=Integer.parseInt(i_exp.get(i + 1));
                switch (i_exp.get(i)){
                    case "+": lh=lh + Integer.parseInt(i_exp.get(i+1));break;
                    case "-": lh=lh - Integer.parseInt(i_exp.get(i+1));break;
                    case "*": lh=lh * Integer.parseInt(i_exp.get(i+1));break;
                    case "/":
                        if (Integer.parseInt(i_exp.get(i+1))==0){
                            rmDic(ids2);
                            rmDic(functions);
                            throw new RuntimeError("Divide by zero",exprType.lineNum);
                        }
                        lh=lh / Integer.parseInt(i_exp.get(i+1));
                        break;
                    case "^": lh= (int) Math.pow(lh , Integer.parseInt(i_exp.get(i+1)));break;
                    case "<": lh= ((lh<rh) ? 1 : 0);break;
                    case ">": lh= ((lh>rh) ? 1 : 0);break;
                    case "==": lh= ((lh==rh) ? 1 : 0);break;
                    case "!=": lh= ((lh!=rh) ? 1 : 0);break;
                    case "<=": lh= ((lh<=rh) ? 1 : 0);break;
                    case ">=": lh= ((lh>=rh) ? 1 : 0);break;
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
                            rmDic(ids2);
                            rmDic(functions);
                            throw new RuntimeError("Divide by zero",exprType.lineNum);
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

    public static List<String> handleIntegerExpr(TreeNode treeNode) throws RuntimeError {
        List child=treeNode.children;
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
    public static List<String> handleIntegerExpr2(TreeNode treeNode) throws RuntimeError {
        List child=treeNode.children;
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
        else if(child.size()==4){
            expr.add(handleFCall((TreeNode)child.get(0),(TreeNode)child.get(2)).toString());
        }
        return expr;
    }
    public static List<String> handleIntegerExpr3(TreeNode treeNode) throws RuntimeError {
        List child=treeNode.children;
        List<String> expr=new ArrayList<>();
        if(child.size()==3){
            expr.add(handleOperation((TreeNode) child.get(0)));
            expr.addAll(handleIntegerExpr2((TreeNode) child.get(1)));
            expr.addAll(handleIntegerExpr3((TreeNode) child.get(2)));
        }
        return expr;
    }
    public static List<String> handleDoubleExpr(TreeNode treeNode) throws RuntimeError {
        List child=treeNode.children;
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
    public static List<String> handleDoubleExpr2(TreeNode treeNode) throws RuntimeError {
        List child=treeNode.children;
        List<String> expr=new ArrayList<>();
        if(child.size()==2){
            expr.add(handleOperation((TreeNode) child.get(0)));
            expr.addAll(handleDoubleExpr((TreeNode) child.get(1)));
        }
        return expr;
    }
    public static String handleStringExpr(TreeNode treeNode) throws RuntimeError {
        List child=treeNode.children;
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
        else if (child.get(0).toString().equals("charAt")){
            return handleStringExpr((TreeNode)child.get(2)).charAt((int) handleExprType((TreeNode)child.get(4)))+"";
        }
        return "";
    }
    public static int handleBooleanExpr(TreeNode treeNode) throws RuntimeError {
        List child=treeNode.children;
        int compare;
        if(child.get(0).toString().equals("S_EXPR")){
            compare = (handleStringExpr((TreeNode) child.get(0))).compareTo(handleStringExpr((TreeNode) child.get(2)));
        }
        else{
            double lh= (double) handleExprType((TreeNode) child.get(0));
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
    public static Object handleID(TreeNode treeNode) throws RuntimeError {
        String id=treeNode.toString();
//        for (Map.Entry<String,Object> o:ids ) {
//            if(o.getKey().equals(id)){
//                return o.getValue();
//            }
//        }
        if(ids2.get(id)!=null){
            return ids2.get(id);
        }
        else {
            rmDic(ids2);
            rmDic(functions);
            throw new RuntimeError("Undeclared variable: " + id, treeNode.lineNum);
        }
    }

    public static void runTree(TreeNode tree) throws RuntimeError {
        List<TreeNode> statements = findSTMTS(tree,new ArrayList<>());
        for (TreeNode statement:statements) {
            if (statement.children.get(0).toString().equals("PRINTSTMT")) {
                handlePrint((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("ASMT")) {
                handleAsmt((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("REASMT")) {
                handleReasmt((TreeNode) statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("EXPR")) {
                handleExpr((TreeNode)statement.children.get(0));
            } else if (statement.children.get(0).toString().equals("if")&& statement.children.size()== 11 && statement.children.get(7).toString().equals("else")) {
                handleIfElse((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5),(TreeNode) statement.children.get(9));
            } else if (statement.children.get(0).toString().equals("if")){
                handleIf((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5));
            }else if (statement.children.get(0).toString().equals("for")){
                handleFor((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(3),(TreeNode) statement.children.get(5),(TreeNode) statement.children.get(8));
            }else if (statement.children.get(0).toString().equals("while")){
                handleWhile((TreeNode) statement.children.get(2),(TreeNode) statement.children.get(5));
            }else if (statement.children.size()>1 && statement.children.get(2).toString().equals("F_CALL_P_LIST")){
                handleVoidFCall((TreeNode) statement.children.get(0),(TreeNode) statement.children.get(2));
            }else if (statement.children.get(0).toString().equals("FUNCTION")){
                handleFunc((TreeNode) statement.children.get(0));
            }
        }
        rmDic(ids2);
        rmDic(functions);
    }
}
