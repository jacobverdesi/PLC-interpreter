import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;


public class TreeNode<T> implements Iterable<TreeNode<T>> {

        T data;
        TreeNode<T> parent;
        List<TreeNode<T>> children;

        public TreeNode(T data) {
            this.data = data;
            this.children = new LinkedList<TreeNode<T>>();
        }

        public TreeNode<T> addChild(T child) {
            TreeNode<T> childNode = new TreeNode<T>(child);
            childNode.parent = this;
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


        // other features ...
    public static void main(String args[]){
        TreeNode<String> root = new TreeNode<String>("root");

        TreeNode<String> node0 = root.addChild("node0");
        TreeNode<String> node1 = root.addChild("node1");
        TreeNode<String> node2 = root.addChild("node2");

        TreeNode<String> node20 = node2.addChild(null);
        TreeNode<String> node21 = node2.addChild("node21");

        TreeNode<String> node210 = node20.addChild("node210");

        System.out.println(root.children);
    }
}

