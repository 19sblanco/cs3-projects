public class LeftistHeap{
    private int size; // number of nodes withing tree
    Node root;

    public LeftistHeap() {
        size = 0;
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
     * merge current leftist heap with new node
     * swap children when null path length of left is smaller than right
     * @param data
     */
    public void insert(int data) {
        Node dataNode = new Node(data);
        root = merge(root, dataNode);
        size++;
    }

    /**
     * finds and deletes max
     * merging 2 sub trees left over
     * @return max
     */
    public int deleteMax() {
        int max = root.data;
        root = merge(root.left, root.right);
        size--;
        return max;
    }

    /**
     * @return int this.size
     */
    public int getSize() {
        return size;
    }

    /**
     * this method combines 2 Leftist Heaps together
     * swapping children when right null path length is greater than left
     */
    private Node merge(Node t1, Node t2) {
        Node small;
        if (t1==null) return t2;
        if (t2==null) return t1;
        if (t1.data > t2.data) {
            t1.right = merge(t1.right, t2);
            small = t1;
        } else {
            t2.right = merge(t2.right, t1);
            small = t2;
        }
        if (getNpl(small.left) < getNpl(small.right)) swapKids(small);
        setNullPathLength(small);
        return small;
    }

    /**
     * switches kids of given node
     * @param t
     */
    private void swapKids(Node t) {
        Node temp = t.right;
        t.right = t.left;
        t.left = temp;
    }

    /**
     * @param Node t
     * @return null path length of t
     */
    private int getNpl(Node t) {
        if (t==null) return -1;
        return t.nullPathLength;
    }

    /**
     * sets null path length of node
     * @param t node
     */
    private int setNullPathLength(Node t) {
        if (t == null) return -1;

        int leftNPL = 1 + setNullPathLength(t.left);
        int rightNPL = 1 + setNullPathLength(t.right);

        t.nullPathLength = Math.min(leftNPL, rightNPL);
        return t.nullPathLength;
    }

    /**
     * Container for leftist heap data
     * null path length: null node = -1, node with no children = 0, shortest path to node without children
     */
    private class Node {
        Node left;
        Node right;
        int nullPathLength;
        int data;
        public Node(int data) {
            this.data = data;
            nullPathLength = 0;
        }
    }
}
