// ******************ERRORS********************************
// Throws UnderflowException as appropriate
// complexity information contained in main method next to residing function

import java.sql.SQLOutput;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     *
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree<E extends Comparable<? super E>> {
    final String ENDLINE = "\n";
    private BinaryNode<E> root;  // Root of tree
    private BinaryNode<E> curr;  // Last node accessed in tree
    private String treeName;     // Name of tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create BST from Array
     *
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.length; i++) {
            bstInsert(arr[i]);
        }
    }

    public static void getLeaves(Integer[] preorder, int beg, int end, ArrayList<Integer> leaves) {
        if (preorder.length == 0) { return; }
        int curr = preorder[beg];
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        // sort the items into a right array and left array
        for(int i = beg + 1; i < preorder.length; i++) {
            if (preorder[i] < curr) {
                left.add(preorder[i]);
            } else {
                right.add(preorder[i]);
            }
        }
        Integer[] leftArray = convertIntegers(left);
        Integer[] rightArray = convertIntegers(right);

        // determine if you call function again or if curr is a leaf
        if (left.size() == 0 && right.size() == 0) {
            leaves.add(curr);
        } else {
            getLeaves(leftArray, 0, left.size(), leaves);
            getLeaves(rightArray, 0, right.size(), leaves);
        }


    }

    /**
     * Create Tree By Level.  Parents are set.
     * This runs in  O(n)
     *
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public void createTreeByLevel(E[] arr, String label) {
        treeName = label;
        if (arr.length <= 0) {
            root = null;
            return;
        }

        ArrayList<BinaryNode<E>> nodes = new ArrayList<BinaryNode<E>>();
        root = new BinaryNode<E>(arr[0]);
        nodes.add(root);
        BinaryNode<E> newr = null;
        for (int i = 1; i < arr.length; i += 2) {
            BinaryNode<E> curr = nodes.remove(0);
            BinaryNode<E> newl = new BinaryNode<E>(arr[i], null, null, curr);
            nodes.add(newl);
            newr = null;
            if (i + 1 < arr.length) {
                newr = new BinaryNode<E>(arr[i + 1], null, null, curr);
                nodes.add(newr);
            }

            curr.left = newl;
            curr.right = newr;
        }
    }

    /**
     * Change name of tree
     *
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a tree with one node per line
     */
    public String toString() {
        if (root == null)
            return (treeName + "\n Empty tree\n");
        else
            return treeName + "\n" + toString(root, "");
    }

    /**
     * This runs in  O(n)
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }

    /**
     * Find successor of "curr" node in tree
     *
     * @return String representation of the successor
     */
    public String successor(BinaryNode<E> t) {
        if (t == null) {
            return "Enter valid node";
        }
        if (curr.parent == null) { // if at the root
            if (curr.right == null) {
                return curr.element + "";
            } else {
                curr = curr.right;
                return min(curr).toString();
            }
        }
        if (curr.right != null) { // if it has a right child
            curr = min(curr.right);
            return curr.toString();
        } else { // if it doesn't
            if (curr.element.compareTo(t.element) >= 0) {
                return successor(t.parent);
            } else {
                if (t.right == null) {
                    curr = t;
                    return t.toString();
                } else {
                    BinaryNode<E> leastRight = min(t.right);
                    if (t.element.compareTo(leastRight.element) < 0) {
                        curr = t;
                        return curr.toString();
                    } else {
                        curr = leastRight;
                        return curr.toString();
                    }
                }
            }
        }
    }

    /**
     * Print all paths from root to leaves
     */
    public void printAllPaths(BinaryNode<E> curr, String path) {
        path += curr.element + " ";
        if (curr.left == null && curr.right == null) { // if both are null (leaf node)
            System.out.println(path);
            return;
        } else if (curr.left != null && curr.right != null) { // if neither are null
            printAllPaths(curr.left, path);
            printAllPaths(curr.right, path);
        }
        if (curr.left == null) { // if the left is null
            printAllPaths(curr.right, path);
        }
        if (curr.right == null) { // if the right is null
            printAllPaths(curr.left, path);
        }
    }

    /**
     * Counts all non-null binary search trees embedded in tree
     *
     * @return Count of embedded binary search trees
     */
    public Integer countBST(BinaryNode<E> curr) { // not finished needs to bst in middle of tree
        if (curr == null) return 0;
        if (curr.left == null && curr.right == null) {
            return 1;
        }
        int count = countBST(curr.left) + countBST(curr.right);
        // if the left tree is a binary tree and the right tree is a binary tree
        if (curr.left != null && isBinary(curr.left) && curr.right != null && isBinary(curr.right)) {
            // if current is less than the right tree, and is greater than the left tree
            if (curr.element.compareTo(max(curr.left).element) > 0 && curr.element.compareTo(min(curr.right).element) < 0) {
                count += 1;
            }
        }
        return count;

    }

    /**
     * Insert into a bst tree; duplicates are allowed
     *
     * @param x the item to insert.
     */
    public void bstInsert(E x) {

        root = bstInsert(x, root, null);
    }

    /**
     * Determines if item is in tree
     *
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(E item) {

        return bstContains(item, root);
    }

    /**
     * Remove all paths from tree that sum to less than given value
     *
     * @param sum: minimum path sum allowed in final tree
     */
    public void pruneK(Integer sum, BinaryNode<E> t) {
        if (t == null) {
            return;
        }
        if (t.left == null && t.right == null) { // if leaf node
            if (sum - (Integer) t.element <= 0) {
                return; // do nothing
            } else {
                // delete me
                if (t.parent == null) {
                    root = null;
                    return;
                } else if (isLeftChild(t)) {
                    t.parent.left = null;
                } else t.parent.right = null;
                // enter method with parent, restoring sum value
                pruneK(sum + (Integer) t.parent.element, t.parent);
            }
        } else {
            pruneK(sum - (Integer) t.element, t.left);
            pruneK(sum - (Integer) t.element, t.right);

        }
    }

    /**
     * Find the least common ancestor of two nodes
     *
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    public BinaryNode<E> lca(E a, E b) { // kickoff for private method lca below
        E bigger, smaller;
        if (a.compareTo(b) < 0) {
            smaller = a;
            bigger = b;
        } else {
            smaller = b;
            bigger = a;
        }
        BinaryNode<E> biggerNode = find(bigger, root);
        return lca(biggerNode, smaller);
    }

    private BinaryNode lca(BinaryNode<E> t, E smaller) {
        if (find(smaller, t.left) != null || t.element == smaller) { // its in the left subtree, or it is that node
            return t;
        } else {
            return lca(t.parent, smaller);
        }
    }

    /**
     * Balance the tree
     */
    public void balanceTree() {
        // grab all tree elements, sorted, store in arraylist
        ArrayList<BinaryNode<E>> empty = new ArrayList<>();
        ArrayList<BinaryNode<E>> treeElements = preOrder(root, empty);

        // create tree with given array
        root = null;
        balanceTree(treeElements, 0, treeElements.size());

    }

    private void balanceTree(ArrayList<BinaryNode<E>> list, int start, int end) {
        if (start >= end) {
            return;
        }
        // insert each midpoint, creating balanced tree
        int mid = (start + end) / 2;
        bstInsert(list.get(mid).element);
        balanceTree(list, start, mid);
        balanceTree(list, mid + 1, end);
    }

    /**
     * In a BST, keep only nodes between range
     *
     * @param a lowest value
     * @param b highest value
     */
    public void keepRange(E a, E b) {
        keepRange(a, b, root);
    }

    public void keepRange(E min, E max, BinaryNode<E> t) {
        if (t == null) { return; }
        if (t.element.compareTo(min) < 0 || t.element.compareTo(max) > 0) { // if not within range
            BinaryNode<E> newT = delete(root, t.element);
            keepRange(min, max, newT);
        }
        keepRange(min, max, t.left);
        keepRange(min, max, t.right);
    }

    /**
     * @return for the level with maximum sum, print the sum of the nodes
     */
    public void maxLevelSum(int count) {
        if (count == 0) { return; }
        System.out.println(count - 1 + "th level " + maxLevelSum(root, 0, count-1));
        maxLevelSum(count-1);
    }

    public int maxLevelSum(BinaryNode<E> t, int pHeight, int currHeight) {
        if (t == null) { return 0; }
        if (currHeight == pHeight) {
            return (Integer) t.element;
        }
        return maxLevelSum(t.left, pHeight, currHeight - 1) + maxLevelSum(t.right, pHeight, currHeight - 1);
    }

    /**
     * @return the sum of the maximum path between any two leaves
     */
    public Integer maxPath() {
        return maxPathSum(root);
    }

    private int maxPathSum(BinaryNode<E> t) {
        if (t == null) { return 0; }
        //do left
        int left = maxPathSum(t.left);
        //do me
        int me = maxPathSum1(t, null);
        //do right
        int right = maxPathSum(t.right);
        int max1 = max(left, right);
        return max(max1, me);
    }

    private int maxPathSum1(BinaryNode<E> t, BinaryNode<E> dontCheck) {
        if (t == null) { return 0; }
        int leftPath = 0;
        int rightPath = 0;
        int parentPath = 0;
        if (t.left != null && t.left != dontCheck) {
            leftPath = (Integer) t.element + maxPathSum1(t.left, t);
        }
        if (t.right != null && t.right != dontCheck) {
            rightPath = (Integer) t.element + maxPathSum1(t.right, t);
        }
        if (t.parent != null && t.parent != dontCheck) {
            parentPath = (Integer) t.element + maxPathSum1(t.parent, t);
        }
        if (leftPath == 0 && rightPath == 0 && parentPath == 0) {
            return (Integer) t.element;
        }
        int maxLR = max(leftPath, rightPath);
        return max(maxLR, parentPath);
    }

    /**
     * @param preorder traversal of a BST,
     *                 print the leaves (without creating the tree)
     */
    public void printLeaves(E[] preorder) {
    }

    /**
     * @return true if the tree is a Sum Tree (every non-leaf node is sum of nodes in subtree)
     */
    public boolean isSum() {
        return isSum(root);
    }

    private boolean isSum(BinaryNode<E> t) {
        if (t == null) { return true; }
        if (t.left == null && t.right == null) { // if leaf node
            return true;
        }
        boolean isSum = treeCount(t.left) + treeCount(t.right) == (Integer) t.element;
        return isSum && isSum(t.left) && isSum(t.right);
    }

    //PRIVATE

    /**
     * @param preorderT  preorder traversal of tree
     * @param postorderT postorder traversal of tree
     * @param name       of tree
     *                   create a full tree (every node has 0 or 2 children) from its traversals.  This is not a BST.
     */
    public void createTreeTraversals(E[] preorderT, E[] postorderT, String name) {
        // todo create new tree
        this.treeName = name;
        root = null;
        // add start of array to tree
        root = createTreeTraversals(preorderT, 0, preorderT.length - 1);
    }
    private BinaryNode<E> createTreeTraversals(E[] preorder, int start, int end) {
        BinaryNode<E> t = new BinaryNode<>(preorder[start]);
        if (start >= end) { return t; }
        if (end - start == 1) { return new BinaryNode<>(preorder[end]); }
        // insert start into tree
        // split array
        start = start + 1;
        int mid = (start + end) / 2;
        // point left to left side of array
        t.left = createTreeTraversals(preorder, start, mid - 1);
        t.right = createTreeTraversals(preorder, mid, end);
        return t;
        // point right to right side of array
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<E> bstInsert(E x, BinaryNode<E> t, BinaryNode<E> parent) {
        if (t == null)
            return new BinaryNode<>(x, null, null, parent);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = bstInsert(x, t.left, t);
        } else {
            t.right = bstInsert(x, t.right, t);
        }

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean bstContains(E x, BinaryNode<E> t) {
        curr = null;
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return bstContains(x, t.left);
        else if (compareResult > 0)
            return bstContains(x, t.right);
        else {
            curr = t;
            return true;    // Match
        }
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     *
     * @param t the node that roots the subtree.
     */
    private String toString2(BinaryNode<E> t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     *
     * @param t the node that roots the subtree.
     */
    private String toString(BinaryNode<E> t, String indent) {
        if (t == null) {
            return "";
        }
        String rightOfMe = toString(t.right, indent + "    ");
        String leftOfMe = toString(t.left, indent + "    ");
        String me;
        if (t.parent != null) {
            me = indent + t.element + "[" + t.parent.element + "]" + "\n";
        } else {
            me = indent + t.element + "[no parent]\n";
        }
        return rightOfMe + me + leftOfMe;
    }

    private int treeCount(BinaryNode<E> t) {
        if (t == null) { return 0; }
        return (Integer) t.element + treeCount(t.left) + treeCount(t.right);
    }

    // MINE / HELPER METHODS

    private BinaryNode<E> min(BinaryNode<E> t) {
        if (t == null) {
            return t;
        }
        if (t.left != null) {
            return min(t.left);
        } else {
            return t;
        }
    }

    private BinaryNode<E> max(BinaryNode<E> t) {
        if (t == null) {
            return t;
        }
        if (t.right != null) {
            return max(t.right);
        } else {
            return t;
        }
    }

    private boolean isLeftChild(BinaryNode<E> t) {
        if (t.parent == null) {
            return false;
        }
        if (t.parent.left == t) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isBinary(BinaryNode<E> t) {
        if (t == null) {
            return true;
        }
        if (t.left != null && t.element.compareTo(t.left.element) > 0) {
            return false;
        } else if (t.right != null && t.element.compareTo(t.right.element) < 0) {
            return false;
        }
        return isBinary(t.left) && isBinary(t.right);
    }

    private BinaryNode<E> find(E data, BinaryNode<E> t) {
        if (t == null) {
            return null;
        }
        if (data == t.element) {
            return t;
        }
        if (data.compareTo(t.element) < 0) {
            return find(data, t.left);
        } else {
            return find(data, t.right);
        }
    }

    private ArrayList<BinaryNode<E>> preOrder(BinaryNode<E> t, ArrayList<BinaryNode<E>> list) {
        if (t == null) {
            return null;
        }
        if (t.left != null) {
            preOrder(t.left, list);
        }
        list.add(t);
        if (t.right != null) {
            preOrder(t.right, list);
        }
        return list;
    }

    private static class BinaryNode<AnyType> {
        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
        BinaryNode<AnyType> parent; //  Parent node

        // Constructors
        BinaryNode(AnyType theElement) {
            this(theElement, null, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt, BinaryNode<AnyType> pt) {
            element = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }


        public String toString() {
            StringBuilder sb = new StringBuilder();
            //sb.append("Node:");
            sb.append(element);
            if (parent == null) {
                sb.append("[]");
            } else {
                sb.append("[");
                sb.append(parent.element);
                sb.append("]");
            }

            return sb.toString();
        }

    }

    public int getHeight(BinaryNode<E> t) {
        if (t == null) { return 0; }
        return 1 + max(getHeight(t.left), getHeight(t.right));
    }

    public int max(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    private static Integer[] convertIntegers(ArrayList<Integer> arrayLst) {
        Integer[] array = new Integer[arrayLst.size()];
        for (int i = 0; i < arrayLst.size(); i++) {
            array[i] = arrayLst.get(i);
        }
        return array;
    }

    public BinaryNode<E> delete(BinaryNode<E> t, E val) {
        if (t == null) {
            return null;
        }
        // search
        if (val.compareTo(t.element) < 0) {
            t.left = delete(t.left, val);
        } else if (val.compareTo(t.element) > 0) {
            t.right = delete(t.right, val);
        } else { // delete
            // 0 to 1 children
            if (t.left == null) {
                return t.right;
            } else if (t.right == null) {
                return t.left;
            }
            // 2 children
            t.element = min(t.right).element;
            t.right = delete(t.right, t.element);
        }
        return t;
    }

    // Test program
    public static void main(String[] args) {

        final String ENDLINE = "\n";


        // Assignment Problem 1
        //toString() complexity: O(n)
        Integer[] v1 = {25, 10, 60, 55, 58, 56, 14, 63, 8, 50, 6, 9, 61};
        Tree<Integer> tree1 = new Tree<Integer>(v1, "Tree1:");
        System.out.println(tree1.toString());
        System.out.println(tree1.toString2());


        // Assignment Problem 2
        //successor complexity: O(n)
        long seed = 436543;
        Random generator = new Random(seed);  // Don't use a seed if you want the numbers to be different each time
        int val = 60;
        final int SIZE = 8;

        List<Integer> v2 = new ArrayList<Integer>();
        for (int i = 0; i < SIZE * 2; i++) {
            int t = generator.nextInt(200);
            v2.add(t);
        }
        v2.add(val);
        Integer[] v = v2.toArray(new Integer[v2.size()]);
        Tree<Integer> tree2 = new Tree<Integer>(v, "Tree2");
        System.out.println(tree2.toString());
        tree2.contains(val);  //Sets the current node inside the tree class.
        int succCount = 5;  // how many successors do you want to see?
        System.out.println("In Tree2, starting at " + val + ENDLINE);
        for (int i = 0; i < succCount; i++) {
            System.out.println("The next successor is " + tree2.successor(tree2.curr));
        }

        // Assignment Problem 3
        // printAllPaths complexity: O(n)
        System.out.println(tree1.toString());
        System.out.println("All paths from tree1");
        tree1.printAllPaths(tree1.root, "");

        // Assignment Problem 4
//        countBST complexity: O(n)
        Integer[] v4 = {66, 75, -15, 3, 65, -83, 83, -10, 16, -7, 70, 200, 71, 90};
        Tree<Integer> treeA = new Tree<Integer>("TreeA");
        treeA.createTreeByLevel(v4, "TreeA");
        System.out.println(treeA.toString());
        System.out.println("treeA Contains BST: " + treeA.countBST(treeA.root));


        Integer[] a = {21, 8, 5, 6, 7, 19, 10, 40, 43, 52, 12, 60};
        Tree<Integer> treeB = new Tree<Integer>("TreeB");
        treeB.createTreeByLevel(a, "TreeB");
        System.out.println(treeB.toString());
        System.out.println("treeB Contains BST: " + treeB.countBST(treeB.root));

        // Assignment Problem 5
        // prunek complexity: o(n)
        System.out.println(treeB.toString());
        treeB.pruneK(60, treeB.root);
        treeB.changeName("treeB after pruning 60");

        System.out.println(treeB.toString());
        treeA.pruneK(200, treeA.root);
        treeA.changeName("treeA after pruning 200");
        System.out.println(treeA.toString());


        // Assignment Problem 6
        // lca complexity: o(n)
        System.out.println(tree1.toString());
        System.out.println("tree1 Least Common Ancestor of (56,61) " + tree1.lca(56, 61) + ENDLINE);
        System.out.println("tree1 Least Common Ancestor of (6,25) " + tree1.lca(6, 25) + ENDLINE);

        // Assignment Problem 7
        // balanceTree complexity: O(n)
        Integer[] v7 = {20, 15, 10, 5, 8, 2, 100, 28, 42};
        Tree<Integer> tree7 = new Tree<>(v7, "Tree7:");

        System.out.println(tree7.toString());
        tree7.balanceTree();
        tree7.changeName("tree7 after balancing");
        System.out.println(tree7.toString());

        // Assignment Problem 8
        // keepRange complexity: O(n)
        System.out.println(tree1.toString());
        tree1.keepRange(10, 50);
        tree1.changeName("tree1 after keeping only nodes between 10 and 50");
        System.out.println(tree1.toString());

        tree7.changeName("Tree 7");
        System.out.println(tree7.toString());
        tree7.keepRange(8, 85);
        tree7.changeName("tree7 after keeping only nodes between 8  and 85");
        System.out.println(tree7.toString());

        // Assignment Problem 9
        // maxLevelSum complexity: O(n)
        Integer[] v9 = {66, -15, -83, 3, -10, -7, 65, 16, 75, 70, 71, 83, 200, 90};
        Tree<Integer> tree4 = new Tree<Integer>(v9, "Tree4:");
        System.out.println(tree4.toString());
        tree4.maxLevelSum(tree4.getHeight(tree4.root));


        // Assignment Problem 10
        // getLeaves complexity: O(n)
        ArrayList<Integer> leaves = new ArrayList<Integer>();
        Integer[] preorder1 = {10, 3, 1, 7, 18, 13};

        getLeaves(preorder1, 0, preorder1.length - 1, leaves);
        System.out.print("Leaves are ");
        for (int leaf : leaves) {
            System.out.print(leaf + " ");
        }
        System.out.println();

        leaves = new ArrayList<Integer>();
        Integer[] preorder2 = {66, -15, -83, 3, -10, -7, 65, 16, 75, 70, 71, 83, 200, 90};

        getLeaves(preorder2, 0, preorder2.length - 1, leaves);
        System.out.print("Leaves are ");
        for (int leaf : leaves) {
            System.out.print(leaf + " ");
        }
        System.out.println();

        // Assignment Problem 11
        // createTreeByLevel complexity: O(n)
        Tree<Integer> treeC = new Tree<Integer>("TreeC");
        Integer[] data = {44, 9, 13, 4, 5, 6, 7};
        treeC.createTreeByLevel(data, "Sum Tree1 ?");
        if (treeC.isSum()) {
            System.out.println(treeC.toString() + " is Sum Tree");
        } else {
            System.out.println(treeC.toString() + " is NOT a Sum Tree");
        }
        Integer[] data1 = {52, 13, 13, 4, 5, 6, 7, 0, 4};
        treeC.createTreeByLevel(data1, "Sum Tree2 ?");
        if (treeC.isSum()) {
            System.out.println(treeC.toString() + " is Sum Tree");
        } else {
            System.out.println(treeC.toString() + " is NOT a Sum Tree");
        }
        Integer[] data2 = {44, 13, 13, 4, 5, 6, 7, 1, 4};
        treeC.createTreeByLevel(data2, "Sum Tree3?");
        if (treeC.isSum()) {
            System.out.println(treeC.toString() + " is Sum Tree");
        } else {
            System.out.println(treeC.toString() + " is NOT a Sum Tree");
        }

        // Assignment Problem 12
        // createTreeBylevel complexity O(n)
        treeC.changeName("Tree12");
        System.out.println(treeC.toString() + "MaxPath=" + treeC.maxPath());


        Integer[] data12 = {1, 3, 2, 5, 6, -3, -4, 7, 8};
        treeC.createTreeByLevel(data12, "Another Tree");
        System.out.println(treeC.toString() + "MaxPath=" + treeC.maxPath());



        // Assignment Problem 13
        // createTreeTraversals complexity: O(n)
        Integer[] preorderT = {1, 2, 4, 5, 3, 6, 8, 9, 7};
        Integer[] postorderT = {4, 5, 2, 8, 9, 6, 7, 3, 1};
        tree1.createTreeTraversals(preorderT, postorderT, "Tree13 from preorder & postorder");
        System.out.println(tree1.toString());
        Integer[] preorderT2 = {5, 10, 25, 1, 57, 6, 15, 20, 3, 9, 2};
        Integer[] postorderT2 = {1, 57, 25, 6, 10, 20, 9, 2, 3, 15, 5};
        tree1.createTreeTraversals(preorderT2, postorderT2, "Tree from preorder & postorder");
        System.out.println(tree1.toString());
    }
}
