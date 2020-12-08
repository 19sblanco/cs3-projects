import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {
                "case1.txt",
                "case2.txt",
                "case3.txt",
                "case4.txt",
                "case5.txt",
                "case6.txt",
                "case7.txt"
        };
        for (String file: files) {
            File fileObj = new File(file);
            Scanner reader = new Scanner(fileObj);

            int voters = Integer.parseInt(reader.nextLine());
            UnionFind unionFind = new UnionFind(voters);
            System.out.println(voters);

            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.equals("")) continue; // does errors if not
                String[] splitLine = line.split(" ");
                int voter1 = Integer.parseInt(splitLine[0]);
                int voter2 = Integer.parseInt(splitLine[1]);
                unionFind.attacks(voter1, voter2);
            }
            unionFind.print();
            System.out.println(file + " Largest group is of size " + unionFind.getLargestGroupSize());
            System.out.println();
        }
    }
}
