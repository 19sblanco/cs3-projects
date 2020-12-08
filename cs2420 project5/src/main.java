import javax.print.DocFlavor;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {
          "paragraph1.txt",
          "paragraph2.txt",
          "paragraph3.txt",
          "p.txt"
        };
        final String DICT_PATH = "dictionary.txt";
        CuckooHashTable<String> dictionary = new CuckooHashTable<>("dictionary");

        long startTime = System.currentTimeMillis();

        fileIntoTable(DICT_PATH, dictionary);
        ArrayList<String> dictArray = dictionary.getCombinedTable();

        for (String file: files) {
            // create table of WordFreq objects containing misspelled words / their frequency
            CuckooHashTable<WordFreq> misspelledTable = checkSpelling(file, dictionary);
            System.out.println(misspelledTable);

            for (WordFreq misspelledWord: misspelledTable.getCombinedTable()) {
                StringBuilder sb = new StringBuilder();
                sb.append("found misspelled :" + misspelledWord.getWord() + ":\n" );
                int closeWords = 0; // keep track of number of words at most 2 errors away

                for (String word: dictArray) { // check distance from every word in dictionary
                    if (word == null) { continue; }
                    int distance = minDistance(misspelledWord.getWord(), word);
                    if (distance <= 2 && closeWords < 10) {
                        sb.append(word + "(" + distance + ") " );
                        closeWords++;
                    } else if (distance <= 1) {
                        sb.append(word + "(" + distance + ") " );
                    }
                }
                System.out.println(sb);
            }
            System.out.println();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (endTime - startTime));
    }
    /**
     * this methods parses a file and populates a hashtable with each word
     * @param filePath
     * @param table
     * @throws FileNotFoundException
     */
    public static void fileIntoTable(String filePath, CuckooHashTable<String> table) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            line = line.toLowerCase().replaceAll("\\p{Punct}", "");
            String[] words = line.split(" ");
            for (String w: words) {
                table.insert(w);
            }
        }
    }
    /**
     * this method gives a list of misspelled words
     * @param filename: file path
     * @param dictionary: hash table containing a dictionary
     * @return ArrayList<String>
     * @throws FileNotFoundException
     */
    public static CuckooHashTable<WordFreq> checkSpelling(String filename, CuckooHashTable<String> dictionary) throws FileNotFoundException {
        CuckooHashTable<WordFreq> misspelledWords = new CuckooHashTable<>(filename);
        File file = new File(filename);
        Scanner reader = new Scanner(file);

        // populates misspelledWords with wordFreq objects
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            line = line.toLowerCase().replaceAll("\\p{Punct}", "");
            String[] words = line.split(" ");
            for (String w: words) {
                if (!dictionary.find(w) && !w.equals("")) {
                    WordFreq wordFreq = new WordFreq(w);
                    WordFreq item1 = misspelledWords.getItem1(wordFreq);
                    WordFreq item2 = misspelledWords.getItem2(wordFreq);
                    if (item1 != null && item1.compareTo(wordFreq) == 0) {
                        item1.addCount();
                    } else if (item2 != null && item2.compareTo(wordFreq) == 0) {
                        item2.addCount();
                    } else {
                        misspelledWords.insert(wordFreq);
                    }
                }
            }
        }
        return misspelledWords;
    }

    /**
     * this method finds the distance in spelling between 2 words
     * @param word1
     * @param word2
     * @return int
     */
    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        //System.out.print( "minDistance " + word1 + " " + word2 + ": ");
        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int delete = dp[i][j + 1] + 1;
                    int insert = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }
        //System.out.println(dp[len1][len2]);
        return dp[len1][len2];
    }
}