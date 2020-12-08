import java.util.ArrayList;

public class MyQueue<T> {
    private ArrayList<T> queue;

    public MyQueue() {
        queue = new ArrayList<>();
    }

    /**
     * adds item to the queue
     * @param num
     */
    public void enqueue(T item) {
        queue.add(item);
    }

    /**
     * removes first item in queue and returns it
     * @return
     */
    public T dequeue() {
        return queue.remove(0);
    }

    /**
     * returns boolean based on if empty
     * @return
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
