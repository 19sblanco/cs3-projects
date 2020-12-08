public class WordFreq implements Comparable<WordFreq> {
    private String word;
    private int count;

    public WordFreq(String word) {
        this.word = word;
        this.count = 1;
    }

    public void addCount() { this.count++; }

    public int getCount() { return this.count; }

    public String getWord() { return this.word; }

    @Override
    public int compareTo(WordFreq o) {
        return this.word.compareTo(o.getWord());
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

    public boolean equals(WordFreq o) {
        return this.word.equals(o.getWord());
    }

    public String toString() {
        return word + "(" + count + ")";
    }
}
