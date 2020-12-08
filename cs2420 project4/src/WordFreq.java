public class WordFreq implements Comparable<WordFreq> {
    private String word;
    private int count;


    public WordFreq(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public void addCount() { this.count++; }

    public int getCount() { return this.count; }

    public String getWord() { return this.word; }

    @Override
    public int compareTo(WordFreq o) {
        if (this.word.equals(o)) {
            return 0;
        } else if (this.word.compareTo(o.toString()) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public String toString() {
        return word + "(" + count + ")";
    }
}
