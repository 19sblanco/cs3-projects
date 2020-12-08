import java.util.ArrayList;

public class UnionFind {
    private int[] union;
    private LinkedList[] attackList;

    /**
     * constuctor
     * @param size how many nodes are going to be in this unionFind obj
     */
    public UnionFind(int size) {
        this.union = new int[size + 1]; // create one size to big, don't use 0th index
        this.attackList = new LinkedList[size + 1];

        for (int i = 1; i < union.length; i++) {
            union[i] = -1;
        }
        for (int i = 1; i < attackList.length; i++) {
            attackList[i] = new LinkedList();
        }
    }

    /**
     * given 2 nodes, finds their roots and unions them
     * @param a
     * @param b
     */
    private void link(int a, int b) {
        int root1 = find(a);
        int root2 = find(b);
        if (root1 == root2) return; // if they are in same group do nothing
        union(root1, root2);
    }

    /**
     * given two roots combines them base on size, smaller one points to the bigger one
     * @param root1
     * @param root2
     */
    private void union(int root1, int root2) {
        if (union[root2] < union[root1]) { // if root 2 is a larger group in size, uses absolute values
            int tmp = union[root1];
            union[root1] = root2;
            union[root2] = union[root2] + tmp;
        } else {
            int tmp = union[root2];
            union[root2] = root1;
            union[root1] = union[root1] + tmp;
        }
    }

    /**
     * find the root of a tree given a node
     * @param i
     * @return base of the tree index
     */
    private int find(int i) {
        if (union[i] < 0) return i;
        union[i] = find(union[i]); // path compression
        return union[i];
    }

    /**
     * checks if nodes are in the same group, do nothing if so
     * if not make them "attack eachother" basically saying that
     * these nodes are in opposite groups
     * @param a node
     * @param b node
     */
    public void attacks(int a, int b) {
        System.out.println("Attack " + a + " " + b);

        if (find(a) == find(b)) {
            System.out.println("Ignored Attack " + a + " " + b);
            return; // do nothing in same group
        }
        LinkedList listA = attackList[a];
        LinkedList listB = attackList[b];

        listA.insert(b); // log that they attacked each other
        listB.insert(a);

        if (listA.size > 1) { // add if element not already in a group
            link(listA.first(), listA.last());
        }
        if (listB.size > 1) {
            link(listB.first(), listB.last());
        }
    }

    /**
     * prints out groups
     */
    public void print() {
        for (int i = 1; i < union.length; i++) {
            if (union[i] > 0) continue; // if not a group
            System.out.println("Group " + find(i) +  " has " + Math.abs(union[i]) + " member(s)");
        }
    }

    /**
     * calculates group with the most nodes
     * @return the size
     */
    public int getLargestGroupSize() {
        int max = -999; // sentinel value
        for (int i = 1; i < union.length; i++) {
            if (union[i] < 0) { // checks if a valid group
                int curr = Math.abs(union[i]);
                if (curr > max) {
                    max = curr;
                }
            }
        }
        return max;
    }
}
