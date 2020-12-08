public class SkewHeap {
    private int size;
    Node root;

    /**
     * merge current skew heap with new node
     * on every level out of tree swap children
     * @param data
     */
    public void insert(int data) {
        Node dataNode = new Node(data);
        root = merge(root, dataNode);
        size++;
    }

    /**
     * finds and deletes min (top of tree)
     * merging 2 sub trees left over
     * @return min
     */
    public int deleteMin() {
        int min = root.data;
        root = merge(root.left, root.right);
        size--;
        return min;
    }

    /**
     * @return int this.size
     */
    public int getSize() {
        return size;
    }

    /**
     * this method combines 2 skew Heaps together
     * swapping children of each node touched
     */
    public Node merge(Node t1, Node t2) {
        Node small;
        if (t1==null) return t2;
        if (t2==null) return t1;
        if (t1.data < t2.data) {
            t1.right = merge(t1.right, t2);
            small = t1;
        } else {
            t2.right = merge(t2.right, t1);
            small = t2;
        }
        swapKids(small);
        return small;
    }

    /**
     * switches kids of given node
     */
    private void swapKids(Node t) {
        Node temp = t.right;
        t.right = t.left;
        t.left = temp;
    }

    /**
     * prints tree to system.out
     */
    public void printTree() {
        printTree(root, "");
    }
    private void printTree(Node t, String indent) {
        if (t == null) return;

        printTree(t.right, indent+"    ");
        System.out.println(indent + t.data);
        printTree(t.left, indent+"    ");
    }

    /**
     * Container for SkewHeap data
     * @param <T>
     */
    private class Node {
        Node left;
        Node right;
        int data;
        public Node(int data) {
            this.data = data;
        }
    }
}
