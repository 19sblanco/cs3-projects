import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;



public class main {
    public static void main(String[] args) throws FileNotFoundException {
        int PRINT_LOOP_COUNT = 25; // after this many loops, print median salary

        DynamicMean dynamicMean = new DynamicMean();
        DecimalFormat ft = new DecimalFormat("$###,###,###");

        String FILE_PATH = "Salaries.txt";
        File file = new File(FILE_PATH);
        Scanner reader = new Scanner(file);
        int salary;
        int loopCount = 0;

        // loop through file print median salary after PRINT_LOOP_COUNT times
        while (reader.hasNextLine()) {
            String line = reader.nextLine();

            if (loopCount == PRINT_LOOP_COUNT) {
                System.out.println("Median Salary is:" + ft.format((dynamicMean.getCurrMedian())));
                loopCount = 0;
            }

            String[] info = line.split("\t");
            if (isDigit(info[0])) { // checks if item is a salary
                salary = Integer.parseInt(info[0]);
                dynamicMean.insert(salary);
                loopCount++;
            }
            System.out.println("Final median salary is: " + ft.format((dynamicMean.getCurrMedian())));

        }





    }
    public static boolean isDigit(String string) {
        char[] characters = string.toCharArray();
        for (char character: characters) {
            if (!Character.isDigit(character)) return false;
        }
        return true;
    }

}
