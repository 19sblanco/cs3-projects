import java.util.ArrayList;

public class MyStack<T> {
    private ArrayList<T> stack;

    public MyStack() {
        stack = new ArrayList<>();
    }

    /**
     * add items into the stack
     * @param item
     */
    public void add(T item) {
        stack.add(item);
    }

    /**
     * clears everything in the stack
     */
    public void clear() {
        stack.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = stack.size() - 1; i >= 0; i--) {
            sb.append(stack.get(i));
            sb.append(" ");
        }
        return sb.toString();
    }
}
