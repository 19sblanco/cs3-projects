public class DynamicMean {
    private int MAX_SIZE_DIFFERENCE = 1;
    private Integer currMedian;
    private SkewHeap minHeap; // storage for numbers greater than median
    private LeftistHeap maxHeap; // storage for numbers less than median

    public DynamicMean() {
        this.minHeap = new SkewHeap();
        this.maxHeap = new LeftistHeap();
    }

    /**
     * returns currentMedian
     * @return Integer
     */
    public Integer getCurrMedian() {
        return currMedian;
    }

    /**
     * insert values into dynamic mean (either minHeap or maxHeap)
     * values greater than median go into the minHeap
     * values less than or equal to median go into maxHeap
     * @param number
     */
    public void insert(int number) {
        if (currMedian == null) { currMedian = number; return; }
        if (number > currMedian) minHeap.insert(number);
        else maxHeap.insert(number);
        if (Math.abs(maxHeap.getSize() - minHeap.getSize()) > MAX_SIZE_DIFFERENCE) {
            shift();
        }
    }

    /**
     * update currMedian by grabbing off the top of larger tree
     * insert old Median into smaller tree
     */
    private void shift() {
        int oldMedian = currMedian;
        if (minHeap.getSize() > maxHeap.getSize()) {
            currMedian = minHeap.deleteMin();
            maxHeap.insert(oldMedian);
        } else {
            currMedian = maxHeap.deleteMax();
            minHeap.insert(oldMedian);
        }
    }
}
