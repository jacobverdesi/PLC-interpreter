import java.util.*;

public class TreeNode<T>{

    private T data;
    private TreeNode<T> parent;
    List<TreeNode<T>> children;
    private int depth;
    int lineNum;
    public TreeNode(T data,int lineNum) {
        this.data = data;
        this.lineNum=lineNum;
        this.children = new LinkedList<>();
        this.depth=0;
    }

    public void addChild(T child,int lineNum) {
        TreeNode<T> childNode = new TreeNode<T>(child,lineNum);
        childNode.parent = this;
        childNode.depth=depth+1;
        if(child instanceof TreeNode){
            for(Object o:((TreeNode) child).children){
                TreeNode t= (TreeNode) o;
                t.depth=depth+2;
                childNode.addChild((T)t,t.lineNum);
            }
        }
        this.children.add(children.size(),childNode);
    }
    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

    static void printTree(TreeNode tree){
        for (int i=0;i<tree.depth;i++){
            System.out.print("   ");
        }
        System.out.println(tree);
        for (Object t: tree.children){
            printTree((TreeNode) t);
        }
    }
    static void reverseTree(TreeNode tree){
        Collections.reverse(tree.children);
        for (Object t: tree.children){
            reverseTree((TreeNode) t);
        }
    }
}

