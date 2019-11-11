import java.util.*;


public class TreeNode<T> implements Iterable<TreeNode<T>> {

    private T data;
    private TreeNode<T> parent;
    List<TreeNode<T>> children;
    private int depth;
    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<>();
        this.depth=0;
    }

    public void addChild(T child) {
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
    }
    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        return new TreeNodeIter<>(this);
    }
    static void printTree(TreeNode tree){
        String s="   ";
        System.out.println(tree.depth+" "+s.repeat(tree.depth)+tree);
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

