import java.util.ArrayList;

public class LinkedList {
    LinkedlistNode root;
    int size;
    ArrayList<Integer> elements;

    public LinkedList() {
        size = 0;
        elements = new ArrayList<>();
    }

    public void insert(int data) {
        LinkedlistNode node = new LinkedlistNode(data);
        node.next = root;
        root = node;
        size++;
        elements.add(data);
    }

    public int first() {
        return root.data;
    }
    public int last() {
        return getLastData(root);
    }

    public int getLastData(LinkedlistNode t) {
        if (t == null) return -999; // error
        if (t.next == null) return t.data;
        return getLastData(t.next);
    }

    public



    class LinkedlistNode {
        LinkedlistNode next;
        int data;
        public LinkedlistNode(int data) {
            this.data = data;
        }
    }
}
