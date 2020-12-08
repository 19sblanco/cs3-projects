// you have to have access to everything in the linked list
//the iterator, you can get rid of
// get me the first thing, get me the next thing, get me the next thing etc

public class MyLinkedList<T> {
    private Node head;
    private Node tail;
    private int size;

    public void addFirst(T data) {
        head = new Node(data, head);
        this.size++;

        // if list has no elements, set tail to point to new added node
        if (tail == null) {
            tail = head;
        }
    }

    public int[] grabListIndexes() {
        int[] idxArray = new int[this.size];
        Node curr = this.head;
        for (int i = 0; i < this.size; i++) {
            idxArray[i] = (int) curr.data;
            curr = curr.next;
        }
        return idxArray;
    }

    public int getSize() { return this.size; }


    public static class Node <T> {
        T data;
        Node<T> next;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
}

