import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
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
        this.children.add(childNode);
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
        for (Object o : tree) {
            TreeNode child = (TreeNode) o;
            String s="   ";
            System.out.println(s.repeat(child.depth)+child);
        }
    }



    // other features ...
    public static void main(String args[]){
        TreeNode<String> root = new TreeNode<String>("root");

        TreeNode<String> node0 = root.addChild("node0");
        TreeNode<String> node1 = root.addChild("node1");
        TreeNode<String> node2 = root.addChild("node2");

        TreeNode<String> node20 = node2.addChild(null);
        TreeNode<String> node21 = node2.addChild("node21");
        TreeNode<String> node22 = node0.addChild("node22");

        TreeNode<String> node210 = node20.addChild("node210");

        //System.out.println(root.children.get(2).children);

        printTree(root);
    }
}

