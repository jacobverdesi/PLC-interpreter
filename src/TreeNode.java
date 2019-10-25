import java.util.*;
import java.util.function.Consumer;


public class TreeNode<T> implements Iterable<TreeNode<T>> {

    T data;
    TreeNode<T> parent;
    List<TreeNode<T>> children;
    int depth;
    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
        this.depth=0;
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        childNode.depth=depth+1;
        if(child instanceof TreeNode){
            for(Object o:((TreeNode) child).children){
                TreeNode t= (TreeNode) o;
                t.depth=depth+2;
                childNode.addChild((T)t);
            }
        }
        this.children.add(children.size(),childNode);
        return childNode;
    }
    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        TreeNodeIter<T> iter = new TreeNodeIter<T>(this);
        return iter;
    }
    public static void printTree(TreeNode tree){
        //System.out.println(tree.depth+" "+s.repeat(tree.depth)+tree);
        Collections.reverse(tree.children);
        for (Object t: tree.children){
            printTree((TreeNode) t);
        }
    }
    public static void reverseTree(TreeNode tree){
        Collections.reverse(tree.children);
        for (Object t: tree.children){
            reverseTree((TreeNode) t);
        }
    }

    // other features ...
    public static void main(String args[]){
        TreeNode root = new TreeNode<String>("root");

        TreeNode node0 = new TreeNode<>("node0");
        TreeNode node1 = new TreeNode<>("node1");
        TreeNode node2 = new TreeNode<>("node2");
        TreeNode node3 = new TreeNode<>("node3");
        TreeNode node4 = new TreeNode<>("node4");
        TreeNode node5 = new TreeNode<>("node5");
        node4.addChild(node5);
        node0.addChild(node3);
        node0.addChild(node4);
        root.addChild(node0);
        root.addChild(node1);
        root.addChild(node2);

        TreeNode newRoot=new TreeNode<>("newRoot");
        //for (Object o : root) {
        //    TreeNode child = (TreeNode) o;
        //    root.addChild(child);
       // }


        newRoot.addChild(root);
        //System.out.println(root.children.get(2).children);
        //printTree(root);
        printTree(newRoot);
    }
}

